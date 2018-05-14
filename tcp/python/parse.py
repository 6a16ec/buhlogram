class flight_information:

	ERROR = False
	
	def __init__(self, string):
		try:
			info = string.split();
			self.coordinate1, self.coordinate2 = float(info[2]), float(info[3])
			self.angle = int(info[4])
			self.height = int(int(info[5]) * 0.3048)
			self.speed = int(info[6])
		except:
			self.ERROR = True

class user_tcp:

	ERROR = False
	commands = {"FlightsByCoordinates": "FlightsByCoordinates", "FlightsByAirlines": "FlightsByAirlines"}

	def __init__(self, string):
		try:
			info = string.split();
			self.command = info[0]

			if(self.command == self.commands["FlightsByCoordinates"]):
				self.coordinate1, self.coordinate2 = float(info[1]), float(info[2])

			if(self.command == self.commands["FlightsByAirlines"]):
				self.airlines = info[1:]

			if(self.command != self.commands["FlightsByCoordinates"] and self.command != self.commands["FlightsByAirlines"]):
				self.ERROR = True

		except:
			self.ERROR = True


class user_information:

	ERROR = False

	def __init__(self, string):
		try:
			info = string.split();
			self.coordinate1, self.coordinate2 = float(info[0]), float(info[1])
		except:
			self.ERROR = True

if(__name__ == "__main__"):
	test = user_tcp("FlightsByAirlines 1.45 3.45\n")
	if(test.ERROR != True):
		if(test.command == test.commands["FlightsByCoordinates"]):
			print(test.coordinate1, test.coordinate2)
		if(test.command == test.commands["FlightsByAirlines"]):
			print(test.airlines)
