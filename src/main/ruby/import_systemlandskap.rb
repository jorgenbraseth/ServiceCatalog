require 'rubygems'
require 'json'
require 'restclient'
require 'benchmark'

def get_systemlandskap(environment, systemlandskap_uri)
  systemlandskap_json = RestClient.get(systemlandskap_uri)
  systemlandskap = JSON.parse(systemlandskap_json)

  processes = [];

  systemlandskap['applikasjonsgrupper'].each do |gruppe|
    applikasjonsgruppenavn = gruppe['navn'];

    gruppe['applikasjoner'].each do |applikasjon|
      applikasjonsnavn = applikasjon['navn']
      versjon = applikasjon['versjon']

      applikasjon['komponenter'].each do |komponent|
        komponent['komponentInstanser'].each do |instans|
          instansId = instans['komponentId']
          unless instans['node']['hostOgPort'].nil?
            hostOgPort = URI('http://'+instans['node']['hostOgPort'])
            host = hostOgPort.host
            port = hostOgPort.port
          end


          providedServices = {}
          instans['tjenester'].each do |tjeneste|
            providedServices[tjeneste['urn']] = tjeneste['uri']
          end

          consumedServices = []
          instans['oppslag'].each do |tjeneste|
            consumedServices << tjeneste['urn']
          end

          processes << {
              :description => instansId,
              :server => host,
              :application => applikasjonsnavn,
              :environment => environment,
              :providedServices => providedServices,
              :consumedServices => consumedServices
          }
        end
      end
    end
  end
  processes
end

def import(environment, systemlandskap_url)
  processes = get_systemlandskap(environment, systemlandskap_url)

  puts "Storing #{processes.size} processes for #{environment}..."
  puts Benchmark.measure {
           RestClient::Request.execute(
               :method => :post,
               :url => 'http://localhost:8080/api/processes',
               :timeout => 300,
               :open_timeout => 300,
               :payload => processes.to_json,
               :headers => {:content_type => 'application/json'}
           )
       }
end

import('ST-N-UTV', 'http://uil0mag-st-n-utv-app01:21180/magdd-st-n-utv/rest/oppslagstjeneste/systemlandskap')
import('ST-F-UTV', 'http://uil0mag-st-f-utv-app01:21180/magdd-st-f-utv/rest/oppslagstjeneste/systemlandskap')
