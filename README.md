# Kodowanie Huffmana

Kodowanie Huffmana jest jedną z metod kompresji bezstratnej. Kompresja bezstratna oznacza, że po dekompresji (procesie odwrotnym do kompresji) stan pliku będzie identyczny, jak przed kompresją. W przypadku zbiorów znaków w pierwszej kolejności sprawdzana jest ilość wystąpień każdej litery ze zbioru, a następnie przypisywane jest im odpowiednia wartość. Znaki występujące najczęściej dostają najkrótsze kody, zaś te występujące rzadziej – dłuższe. W tym celu wykorzystuje się drzewo binarne, w które wpisywane są znaki i ich częstotliwości wystąpień.
Działanie algorytmu możemy rozpisać następująco:
- Pobierz tekst od użytkownika;
-  Zlicz ilość wystąpień dla każdego znaku;
- Dla każdego znaku utwórz liście drzewa binarnego; wartością liścia jest ilość wystąpień dla
danego znaku
- Wybierz dwa liście o najmniejszych wartościach
- Utwórz nowy liść, który będzie kontenerem dla wybranych liści; jego wartość jest sumą ich
wartości; mniejszy element będzie dzieckiem po lewo, większy dzieckiem po prawo
- Powtarzaj kroki 4-5, aż nie pozostanie jeden element – korzeń drzewa.
Powyższa procedura kodowania jest całkowicie odwracalna, o ile nadal mamy dostęp do drzewa lub
tablicy kodowania.

Kodowanie Huffmana jest przykładem algorytmu zachłannego. Algorytm zachłanny dokonuje zawsze wyboru, który wydaje się w danej chwili najkorzystniejszy. Tak więc, dokonywany jest lokalnie wybór optymalny w nadziei, że doprowadzi to do globalnie optymalnego rozwiązania. Zachłanny algorytm Huffmana oblicza optymalny sposób reprezentacji znaków za pomocą ciągów bitów, wykorzystując tablicę częstości występowania znaków. W algorytmie występują tak zwane kody prefiksowe których właściwością jest to, że żadne słowo kodowe nie jest prefiksem innego słowa kodowego. Do tworzenia optymalnych kodów binarnych znaków wykorzystuje się drzewo binarne. Optymalny kod jest zawsze reprezentowany przez regularne drzewo binarne, w którym każdy węzeł wewnętrzny ma dwóch synów. Kodowanie Huffmana jest algorytmem zachłannym, ponieważ budując drzewo metodą wstępującą, od liści do korzenia, w pierwszej kolejności brane są liście, których wartości są najmniejsze, co jest optymalnym lokalnym wyborem realizowanym poprzez kolejkę priorytetową.

