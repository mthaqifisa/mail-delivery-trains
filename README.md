# mail-delivery-trains
### BigPay Assignment 2024

URL:
```text
[POST] http://localhost:8088/api/solve
```

sample payload:
```json
{
  "nodes": ["A", "B", "C"],
  "edges": [
    {"name": "E1", "node1": "A", "node2": "B", "journeyTimeInSeconds": 30},
    {"name": "E2", "node1": "B", "node2": "C", "journeyTimeInSeconds": 10}
  ],
  "trains": [
    {"name": "Q1", "capacityInKg": 6, "startingNode": "B"}
  ],
  "packages": [
    {"name": "K1", "weightInKg": 5, "startingNode": "A", "destinationNode": "C"}
  ]
}
```