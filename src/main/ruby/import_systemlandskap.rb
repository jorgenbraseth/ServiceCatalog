require 'rubygems'
require 'json'
require 'restclient'
require 'benchmark'

def map_process_data(environment, systemlandskap)
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

def get_systemlandskap(environment, systemlandskap_uri)
  systemlandskap_json = RestClient.get(systemlandskap_uri)
  systemlandskap = JSON.parse(systemlandskap_json)

  map_process_data(environment, systemlandskap)
end

def get_systemlandskap_from_file(environment, path_to_file)
  systemlandskap_json = File.read(path_to_file)
  systemlandskap = JSON.parse(systemlandskap_json)

  map_process_data(environment, systemlandskap)
end

def upload_process_data(environment, processes)
  puts "Storing #{processes.size} processes for #{environment}..."
  puts Benchmark.measure {
         until(processes.empty?)
           RestClient::Request.execute(
               :method => :post,
               :url => 'http://localhost:8080/api/processes',
               :timeout => 3000,
               :open_timeout => 3000,
               :payload => processes.slice!(0,20).to_json,
               :headers => {:content_type => 'application/json'}
           )
         end
       }
end

def import(environment, systemlandskap_url)
  processes = get_systemlandskap(environment, systemlandskap_url)

  upload_process_data(environment, processes)
end

def import_from_file(environment, path_to_file)
  processes = get_systemlandskap_from_file(environment, path_to_file)

  upload_process_data(environment, processes)
end

import('ST-N-UTV', 'http://uil0mag-st-n-utv-app01:21180/magdd-st-n-utv/rest/oppslagstjeneste/systemlandskap')
import('ST-F-UTV', 'http://uil0mag-st-f-utv-app01:21180/magdd-st-f-utv/rest/oppslagstjeneste/systemlandskap')
import_from_file('ST-N-S', '../resources/oppslagstjeneste-dumps/systemlandskap-st-n-s.json')
import_from_file('ST-F-S', '../resources/oppslagstjeneste-dumps/systemlandskap-st-f-s.json')
import_from_file('GP-N-S', '../resources/oppslagstjeneste-dumps/systemlandskap-gp-n-s.json')
import_from_file('GP2-N-S', '../resources/oppslagstjeneste-dumps/systemlandskap-gp2-n-s.json')
import_from_file('GP3-N-S', '../resources/oppslagstjeneste-dumps/systemlandskap-gp3-n-s.json')
import_from_file('GP4-N-S', '../resources/oppslagstjeneste-dumps/systemlandskap-gp4-n-s.json')
import_from_file('AT-N-S', '../resources/oppslagstjeneste-dumps/systemlandskap-at-n-s.json')
import_from_file('PROD', '../resources/oppslagstjeneste-dumps/systemlandskap-prod.json')
