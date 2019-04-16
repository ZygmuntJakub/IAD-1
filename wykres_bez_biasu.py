import matplotlib.pyplot as plt
import sys
import csv

x = []
y = []

x1 = []
y1 = []

x2 = []
y2 = []

with open(sys.argv[1],'r') as csvfile:
    plots = csv.reader(csvfile, delimiter=';')
    for row in plots:
        x.append(int(row[0]))
        y.append(float(row[1]))

with open(sys.argv[2],'r') as csvfile:
    plots = csv.reader(csvfile, delimiter=';')
    for row in plots:
        x1.append(int(row[0]))
        y1.append(float(row[1]))

with open(sys.argv[3],'r') as csvfile:
    plots = csv.reader(csvfile, delimiter=';')
    for row in plots:
        x2.append(int(row[0]))
        y2.append(float(row[1]))
plt.plot(x,y, label='1 neuron')
plt.plot(x1,y1, label='2 neurony')

plt.plot(x2,y2, label='3 neurony')
plt.xlabel('Kolejne iteracje')
plt.ylabel('Blad sredniokwadratowy')
plt.title('Bez biasu\nMomentum = 0.0\nWspolczynnik nauki = 0.1')
plt.axis([-100,10100,-0.05,0.7])
#plt.text(3000, 0.371932, "0.371932", fontdict=None, withdash=False)
#plt.text(3000, 0.037547, "0.037547", fontdict=None, withdash=False)
#plt.text(3000, 0.005189, "0.005189", fontdict=None, withdash=False)
plt.grid(True)
plt.legend()
plt.show()