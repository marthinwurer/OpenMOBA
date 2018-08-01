
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

map_array = []
for row in range(height):
    row_array = []
    for column in range(width):
        index = row * width + column
        row_array.append(int(separate[index].strip()))
    map_array.append(row_array)
    # print(row_array)

# set the outer edge
edge_value = 221
center_value = 588
path_value = 212
water_value = 280
edge_width = 16
path_width = 5
diagonal_width = 4
for row in range(height):
    for column in range(width):

        # do the outer edge
        if row < edge_width or row > height - edge_width or\
                column < edge_width or column > width - edge_width:
            map_array[row][column] = edge_value
        else:

            path_edge = edge_width + path_width
            if row < path_edge or row > height - path_edge or \
                    column < path_edge or column > width - path_edge:
                map_array[row][column] = path_value

            elif abs(column - row) < diagonal_width:
                map_array[row][column] = water_value
            elif abs(width - column - row) < diagonal_width:
                map_array[row][column] = path_value

            else:
                map_array[row][column] = center_value
    print(map_array[row])

# for column in range(width):
#     print(width - column)

out_text = "\n"
for row in range(height):
    out_text += str(map_array[row])[1:-1] + ",\n"

out_text = out_text[:-2]
print(out_text)

data.text = out_text

tree.write("../assets/3lanemap_out.tmx")

with open("../assets/3lanemap_out.csv", 'w') as f:
    f.write("%s,%s\n" % (height, width))
    for row in range(height):
        out_text = str(map_array[row])[1:-1] + "\n"
        f.write(out_text)

