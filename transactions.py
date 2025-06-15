TransactionList = input().split(",")
TCount = 0
conflict = False

for t in TransactionList:
    trans_id = int(t[1])
    if trans_id > TCount:
        TCount = trans_id

for i in range(len(TransactionList)):
    op1, tid1, data1 = TransactionList[i][0], TransactionList[i][1], TransactionList[i][2]
    for j in range(i + 1, len(TransactionList)):
        op2, tid2, data2 = TransactionList[j][0], TransactionList[j][1], TransactionList[j][2]
        
        if tid1 != tid2 and data1 == data2:
            if op1 == 'w' or op2 == 'w':
                conflict = True

print(TCount)
print(0 if conflict else 1)
