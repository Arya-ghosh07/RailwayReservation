import random
import math

stations = []
# Major WB Stations
wb_majors = [
    ("HWH", "Howrah", 500, 400), ("SDAH", "Sealdah", 530, 400), 
    ("KGP", "Kharagpur", 350, 500), ("ASN", "Asansol", 350, 250), 
    ("BWN", "Bardhaman", 450, 300), ("MLDT", "Malda Town", 480, 150),
    ("NJP", "New Jalpaiguri", 480, 50), ("SGUJ", "Siliguri", 500, 40),
    ("DGR", "Durgapur", 400, 260), ("KOAA", "Kolkata", 520, 380),
    ("BPC", "Berhampore Court", 520, 220), ("KWAE", "Katwa", 500, 260),
    ("BQA", "Bankura", 350, 350), ("PRR", "Purulia", 250, 320),
    ("MDN", "Midnapore", 350, 480), ("TKG", "Thakurganj", 520, 60),
    ("SNT", "Sainthia", 450, 240), ("RPH", "Rampurhat", 450, 200),
    ("KNE", "KishanGanj", 480, 100), ("JAL", "Jalpaiguri", 490, 70),
    ("APDJ", "Alipurduar", 600, 60), ("NMZ", "New Mal Jn", 550, 50),
    ("HOO", "Hooghly", 500, 360), ("BDC", "Bandel", 490, 350),
    ("KLYM", "Kalyani", 530, 350), ("NH", "Naihati", 540, 360),
    ("BKP", "Barrackpore", 535, 380), ("DDJ", "Dum Dum", 530, 390),
    ("SHM", "Shalimar", 500, 420), ("SRC", "Santragachi", 480, 410)
]
stations.extend(wb_majors)

# Add some Local WB stations
for i in range(1, 31):
    x = random.randint(300, 600)
    y = random.randint(40, 550)
    stations.append((f"WBL{i}", f"WB Local {i}", x, y))

# Outside WB Stations
outside = [
    ("NDLS", "New Delhi", 100, 100), ("BCT", "Mumbai Central", 150, 600),
    ("MAS", "Chennai Central", 300, 800), ("SBC", "KSR Bengaluru", 200, 750),
    ("PNBE", "Patna Jn", 300, 150), ("GHY", "Guwahati", 700, 100),
    ("BBS", "Bhubaneswar", 400, 600), ("LKO", "Lucknow", 200, 150),
    ("BSB", "Varanasi", 250, 200), ("PUNE", "Pune Jn", 150, 550)
]
stations.extend(outside)

# Generate Edges
edges = []
# Connect WB stations heavily
for i in range(len(wb_majors)):
    for j in range(i+1, len(wb_majors)):
        if random.random() < 0.2: # 20% chance of direct connection
            s1 = wb_majors[i]
            s2 = wb_majors[j]
            dist = math.hypot(s1[2] - s2[2], s1[3] - s2[3]) * 1.5
            time = int(dist * 1.2)
            edges.append((s1[0], s2[0], round(dist, 1), time))
            edges.append((s2[0], s1[0], round(dist, 1), time))

# Connect locals to nearest major
for i in range(30, 60): # the locals
    local = stations[i]
    # find closest major
    closest = min(wb_majors, key=lambda m: math.hypot(local[2]-m[2], local[3]-m[3]))
    dist = math.hypot(local[2]-closest[2], local[3]-closest[3]) * 1.5
    time = int(dist * 1.5) # locals are slower
    edges.append((local[0], closest[0], round(dist, 1), time))
    edges.append((closest[0], local[0], round(dist, 1), time))

# Connect outside stations to WB
wb_hubs = ["HWH", "SDAH", "KGP", "ASN", "NJP"]
for out in outside:
    # connect to 2 hubs
    for hub in random.sample(wb_hubs, 2):
        s1 = out
        s2 = next(s for s in stations if s[0] == hub)
        dist = math.hypot(s1[2] - s2[2], s1[3] - s2[3]) * 4.0 # outside is far
        time = int(dist * 0.8) # express trains
        edges.append((s1[0], s2[0], round(dist, 1), time))
        edges.append((s2[0], s1[0], round(dist, 1), time))

# Generate Trains
train_types = ["Express", "Superfast", "Local", "Special", "Rajdhani", "Shatabdi", "Vande Bharat"]
trains = []
for i in range(1, 101):
    t_type = random.choice(train_types)
    src = random.choice(stations)
    dst = random.choice(stations)
    while src == dst:
        dst = random.choice(stations)
    
    t_num = str(10000 + i)
    t_name = f"{src[1]}-{dst[1]} {t_type}"
    seats = random.randint(200, 1000)
    fare = round(random.uniform(50.0, 3000.0), 1)
    if t_type == "Local":
        fare = round(random.uniform(10.0, 100.0), 1)
        seats = 1500
    
    freq = random.choice(["Daily", "Mon,Wed,Fri", "Sun", "Sat,Sun", "Except Tue"])
    trains.append(f"{t_num}|{t_name}|{src[0]}|{dst[0]}|{seats}|{fare}|{freq}")

with open("data/stations.txt", "w") as f:
    for s in stations:
        f.write(f"{s[0]}|{s[1]}|{s[2]}|{s[3]}\n")

with open("data/edges.txt", "w") as f:
    for e in edges:
        f.write(f"{e[0]}|{e[1]}|{e[2]}|{e[3]}\n")

with open("data/trains.txt", "w") as f:
    for t in trains:
        f.write(t + "\n")

print("Data generation complete.")
