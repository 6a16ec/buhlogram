import flightradar24, math, time
import config, file, output, parse

def getGlobalAirlines(fr24):
	airlines = fr24.get_airlines()
	airlines_string = ""
	for airline in airlines['rows']:
		airlines_string += airline['ICAO']
		if(airline['ICAO'] != airlines['rows'][len(airlines['rows']) - 1]['ICAO']):
			airlines_string += " "
	file.create(config.airlines_file, airlines_string)

def getAirlines(format = "array"):
	airlines_string = file.readLine(config.airlines_file)
	
	if(format == "array"): 
		airlines = airlines_string.split(" ")
		return airlines

	if(format == "string"):
		return airlines_string

def getGlobalFlight(fr24, airline):

	while 1:
		try:
			flights = fr24.get_flights(airline)
			flights_string = ""

			for flight in flights:
				if(flight != "full_count" and flight != "version" and flight != "stats" and flight != "visible"):
					flights_string += str(flight) + " "
					for data in flights[flight]:
						flights_string += str(data)
						if(data != flights[flight][len(flights[flight]) - 1]):
							flights_string += " "
						else:
							flights_string += "\n"
			flights_string = flights_string[:-1] # govnokod, you know

			file.create(config.flights_dir + airline, flights_string)
			break
			
		except:
			output.msg("getGlobalFlight() error")
			time.sleep(5)

def getGlobalFlights(fr24):

	airlines = getAirlines()
	
	for airline in airlines:
		getGlobalFlight(fr24, airline)
		# output.msg(airline) 
		

def findByСoordinate(user_information):
	flights_near = []

	airlines = getAirlines()
	for airline in airlines:
		flights = file.readLines(config.flights_dir + airline)
		for flight_information in flights:
			if isFlightNear(user_information, flight_information):
				flights_near.append(flight_information)
	return flights_near

def isFlightNear(user_information, flight_information):

	flight = parse.flight_information(flight_information); user = parse.user_information(user_information)
	if(flight.ERROR or user.ERROR): return False

	if(math.sqrt(pow(flight.coordinate1 - user.coordinate1, 2) + pow(flight.coordinate2 - user.coordinate2, 2)) <= 1): return True
	else: return False; 



def main():
	fr24 = flightradar24.Api()
	# getGlobalAirlines(fr24)
	# print(getAirlines())

	getGlobalFlights(fr24)
	# getGlobalFlight(fr24, "AFL")

	for line in findByСoordinate("56.3268 44.0059"):
		flight = parse.flight_information(line)
		print(line)
		print(flight.height)


if(__name__ == "__main__"): main()
