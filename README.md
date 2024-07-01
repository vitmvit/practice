# Practice

Консольный калькулятор валют  
Функционал:  
• Калькулятор должен умеет работать с двумя валютами — доллар и рубль — и позволяет выполнять операции сложения и
вычитания.  
• Складывать и вычитать можно только значения в одной валюте.  
• Реализована операция конвертации из одной валюты в другую по курсу, который задается во внешнем файле конфигурации.  
• Реализована поддержка не только целых, но и дробных значений.

Замечания:  
• Значение в долларах обозначается символом $, расположенным перед числом (например, $57.75).  
• Значение в рублях — символом "р", расположенным после числа (например, 57.75р).  
• Операция конвертации долларов в рубли — toRubles($57.75), рублей в доллары — toDollars(57.75р).  
• Разделитель целой и дробной части ".".

Примеры консольного ввода:  
$5 => $5  
5р => 5р  
$5-$4.9 => $0.10  
5р+4.9р => 9.90р  
$5-$4.9+$3 => $3.10  
toDollars(5р+4.9р) => $0.36  
toRubles($5-$4.9) => 0.29р  
toDollars(5р+4.9р + toRubles($5-$4.9)) => $0.37  
toDollars(5р+4.9р - toRubles($5-$4.9)) => $0.35  

