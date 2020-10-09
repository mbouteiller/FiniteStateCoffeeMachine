# FiniteStateCoffeeMachine
Coffee Machine in Java using finite State Machines 

Thermodynamics :

*Hypotheses :

-Electric consumption of a coffee maker : 1,133 kwh ( This will be used as the power of the heater)
Source : https://iea-etsap.org/E-TechDS/PDF/R10_electric%20appliances_FINAL_GSOK.pdf

-Room temperature : 20°C

*Formula :

Time to heat = (Chosen Temperature - Room Temperature) / (Electric Consumption * 860 / Water Volume ) * 3600

with :

- Time To Heat in seconds
- Chosen Temperature in °C
- Room Temperature = 20°C
- Electric Consumption in kwh
- 860 = number of Kcal in 1Kw
- Water Volume in liter

For example : 

+--------------------+----+------+------+------+
| volume\Temperature | 20 | 35   | 60   | 85   |
+--------------------+----+------+------+------+
| 0.1                | 0  | 5.6  | 14.8 | 24.1 |
+--------------------+----+------+------+------+
| 0.2                | 0  | 11.1 | 29.6 | 48.2 |
+--------------------+----+------+------+------+
| 0.33               | 0  | 18.3 | 48.9 | 79.5 |
+--------------------+----+------+------+------+