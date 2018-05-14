import time
import datetime

def date(hours, minutes, seconds, day, mounth, year):
	date = date(year, mounth, day)
	unixtime = time.mktime(date.timetuple()) + 60*60*hours + 60*minutes + seconds;
	return int(unixtime)

def now():
	return int(time.time())


def toDate(unixtime):
	unixtime = int(unixtime)

	array =  datetime.datetime.fromtimestamp(unixtime).strftime('%d %m %Y %H %M %S %A').split(" ")
	day = int(array[0]); month = int(array[1]); year = int(array[2]); hour = int(array[3]); minute = int(array[4]); second = int(array[5]); weekday = array[6]
	return day, month, year, hour, minute, second, weekday

def toDateString(unixtime):
	return str(datetime.datetime.fromtimestamp(unixtime).strftime('%d.%m.%Y %H:%M:%S %A'))

if (__name__ == "__main__"): print(toDateString(1522321260))
