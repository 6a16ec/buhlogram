import output

def readLines(name):
	name = str(name)

	if(not empty(name)):
		with open(name, "r") as file:
			lines = file.readlines()
			for i in range(len(lines)):
				lines[i] = lines[i].replace("\n", "")

			return lines;

	else: return []

def readLine(name, number = 0):
	name = str(name)
	number = int(number)

	if(not empty(name)):
		lines = readLines(name)
		return lines[number]
	else: return ""

def create(name, text):
	name = str(name)
	text = str(text)

	try:
		with open(name, "w") as file:
			file.write(text)
	except:
		output.msg("Error in create() with file: " + name)

def changeLine(name, number, text, new_line = True):
	name = str(name)
	number = int(number)
	text = str(text)

	lines = readLines(name)

	string_lines = ""
	for i in range(len(lines)):
		if(i == number): 
			string_lines += text
			if(new_line): string_lines += "\n"
		else: string_lines += str(lines[i])

	create(name, string_lines)

def empty(name):
	name = str(name)

	try:
		with open(name, "r") as file:
			lines = file.read()
		if(len(lines) > 0): return False
		else: return True
	except:
		output.msg("Error in readLines() with opening file: " + str(name))
		return True

def delete(name):
	name = str(name)
	create(name, "")

def pushFront(name, text, new_line = True):
	name = str(name)
	text = str(text)

	lines = readLines(name)
	string_lines = text
	if(new_line): string_lines += "\n"
	for line in lines: string_lines += str(line)
	create(name, string_lines)

def pushBack(name, text, new_line = True):
	name = str(name)
	text = str(text)

	lines = readLines(name)

	string_lines = ""
	for line in lines: string_lines += str(line)
	string_lines += text
	if(new_line): string_lines += "\n"

	create(name, string_lines)

def popFront(name):
	name = str(name)

	pop_line = readLine(name)
	lines = readLines(name)

	string_lines = ""
	for i in range(len(lines)):
		if(i == 0): first = False
		else: string_lines += str(lines[i])

	create(name, string_lines)

	return pop_line

def copy(name, name_copy):
	lines = readLines(name)

	string_lines = ""
	for line in lines: string_lines += str(line)

	create(name_copy, string_lines)



if (__name__ == "__main__"):
	# print(readLines(789))
	# print(empty("WorkFiles/posts.new"))
	# delete("ConfigFiles/group.ids")
	# print(empty("ConfigFiles/group.ids"))
	# pushFront("ConfigFiles/group.ids", "000")
	# print(popFront("WorkFiles/posts.new"))
	# pushBack("WorkFiles/posts.new", "000")
	# changeLine("WorkFiles/posts.new", 0, "000")
	readLines("WorkFiles/posts.new")
	readLines("WorkFiles/posts.new")
