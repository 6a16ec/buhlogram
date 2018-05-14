import time
import socket,flightradar24
import flights, parse, config, file


def main():
    fr24 = flightradar24.Api()

    sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    # sock.bind( ("192.168.1.111", 777) )
    sock.bind( ("", 777) )
    sock.listen(5)
    while 1:
        conn, addr = sock.accept()
        data = conn.recv(16384)
        udata = data.decode("utf-8")

        user_info = parse.user_tcp(udata)
        answer = []

        if(user_info.ERROR != True):

            if(user_info.command == user_info.commands["FlightsByCoordinates"]): 
                answer = flights.findByСoordinate(str(user_info.coordinate1) + " " + str(user_info.coordinate2))
                
            if(user_info.command == user_info.commands["FlightsByAirlines"]):
                for airline in user_info.airlines:
                    flights.getGlobalFlight(fr24, airline)
                    answer += file.readLines(config.flights_dir + airline)
        else: answer = ["error"]

        answer_string = ""
        for answer_one in answer:
            answer_string += answer_one + "\n"

        conn.send(bytes(answer_string, 'utf-8'))
        conn.close()


if(__name__ == "__main__"): main()
