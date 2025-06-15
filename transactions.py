TransactionList = input("").split(",")
count =0
TCount =0
loop=1
for i in range TransactionList.len:
    ThisTInfo=TransactionList[i].split("")
    
    if(int(ThisTInfo[1])>TCount):
        TCount = int(ThisTInfo[1])
    for j in range TransactionList.len:
        compareTInfo =TransactionList[j].split("")
        if(ThisTInfo[2] == compareTInfo[2] and ThisTInfo[1] == compareTInfo[1]):
            continue
        elif(ThisTInfo[2] == compareTInfo[2] and ThisTInfo[1] != compareTInfo[1] and (ThisTInfo[0] =='w' or compareTInfo[0] == 'w')):
            # ThisTInfo[0] or compareTInfo[0] the one that came first has a flash to the other one
    # if loop than loop=0
print(TCount,\n loop)