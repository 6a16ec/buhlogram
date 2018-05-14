import config
import unixtime

def msg(message):
	message = "[" + str(unixtime.toDateString(unixtime.now())) + "]" + " " +str(message)
	if(config.output_place == config.output_console): print(message)

if(__name__ == "__main__"): msg("Test message")
