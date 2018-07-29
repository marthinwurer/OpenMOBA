
import xml.etree.ElementTree as etree


tree = etree.parse("../assets/3lanemap.tmx")
root = tree.getroot()
for i in root:
    print(i)
layer = root.find('layer')
data = layer.find('data')

map = root[1]
width = int(map.attrib["width"])
height = int(map.attrib["height"])

print(width, height)

print(data)
text = data.text
# print(text)
print(type(text))
separate = text.split(",")
print(len(separate))
# for line in text.split('\n'):
#     print()
#     print(line)




# rows = []
# for line in f:
#     rows.append(line.split(','))
# print(rows)

