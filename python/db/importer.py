import csv
import psycopg2
myfile = csv.DictReader(open('data.csv', 'rb'), delimiter=',', quotechar='"')
conn = psycopg2.connect("host='localhost' dbname='pythontest' user='hxu'")
cur = conn.cursor()
for row in myfile:
    print "Inserting row"
    print row
    cur.execute("INSERT INTO test1 (str, nmbr, longerstring) VALUES (%(str)s, %(nmbr)s, %(longerstring)s);", row)
    print cur.query

conn.commit()
