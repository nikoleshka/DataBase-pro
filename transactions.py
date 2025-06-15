TransactionList = input().split(",")
TCount = 0
edges = set()
transactions = set()

for t in TransactionList:
    trans_id = int(t[1])
    transactions.add(trans_id)
    TCount = max(TCount, trans_id)

for i in range(len(TransactionList)):
    op1, tid1, data1 = TransactionList[i][0], TransactionList[i][1], TransactionList[i][2]
    for j in range(i + 1, len(TransactionList)):
        op2, tid2, data2 = TransactionList[j][0], TransactionList[j][1], TransactionList[j][2]
        if tid1 != tid2 and data1 == data2 and ('w' in [op1, op2]):
            edges.add((int(tid1), int(tid2)))

from collections import defaultdict

graph = defaultdict(list)
for u, v in edges:
    graph[u].append(v)

def has_cycle(graph, nodes):
    visited = set()
    rec_stack = set()

    def dfs(node):
        visited.add(node)
        rec_stack.add(node)
        for neighbor in graph[node]:
            if neighbor not in visited:
                if dfs(neighbor):
                    return True
            elif neighbor in rec_stack:
                return True
        rec_stack.remove(node)
        return False

    for node in nodes:
        if node not in visited:
            if dfs(node):
                return True
    return False

print(TCount)
print(0 if has_cycle(graph, transactions) else 1)
