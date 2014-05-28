Developers Influyentes 
===============

Developers Influyentes es la iniciativa de brindar a la comunidad de Developers Dominicanos una herramienta que pueda servir de base estadística para medir la actividad, popularidad y calidad de los contenidos expuestos en los distintos grupos cabecera y focus group. 
 
La aplicación puede ser accedida desde http://50.19.213.136:8080/developer-influencer  

La misión principal de la aplicación es incentivar la sana competitividad, el saneamiento de las publicaciones de los desarrolladores así como motivar a la participación en otros grupos del colectivo Dominicano. 

En la actualidad, la aplicación recopila data diariamente de los siguientes grupos de Developers Dominicanos: 

+ [#VivaPHP](https://www.facebook.com/groups/jhdtujtr/)  
  - Administradores: [Juan Manuel B R](https://www.facebook.com/juanmboehme), [Jean Carlos Almeyda](https://www.facebook.com/lerebourss), [Elminson De Oleo Baez](https://www.facebook.com/elminson) y [Elias Tejeda](https://www.facebook.com/juanelias.tejeda).

+ [C#.do](https://www.facebook.com/groups/csharp.do/) 
  - Administradores: [Ahmed Ayub](https://www.facebook.com/amhedh), [Misael Difo Contrera](https://www.facebook.com/misaedifo), [Gregory Suarez](https://www.facebook.com/gregory.suarez.18), y [Joaquin Reynoso](https://www.facebook.com/joaquin.reynoso.75).

+ [Caribbean SQL](https://www.facebook.com/groups/179210165492903/)
  - Administradores: [Alberto Morillo Rodríguez](https://www.facebook.com/alberto.morillo.sqlserver), [Diomedes Ignacio Domínguez Ureña](https://www.facebook.com/diomedesignacio.dominguezurena), [Alberto Santiago](https://www.facebook.com/alberto.santiago.7906), [Enmanuel Madrigal](https://www.facebook.com/enmanuel.madrigal.3) y [Alberto Orlando Sepulveda Suarez](https://www.facebook.com/alsepulved).

+ [CodigoLibre Developers](https://www.facebook.com/groups/358999187465748/)  
  - Administradores: [Carlos Camacho](https://www.facebook.com/vacax), [Jose Paredes](https://www.facebook.com/jose.paredes.712), [Carlos Estevez](https://www.facebook.com/carlos.estevez.3551) y [Antonio Perpinan](https://www.facebook.com/antonio.perpinan).

+ [Developers Dominicanos](https://www.facebook.com/groups/devdominicanos/)  
  - Administradores: [Efraín Reyes](https://www.facebook.com/Efrax86), [Juan Miguel Calcaño](https://www.facebook.com/Asinox), [Mayreni Vargas](https://www.facebook.com/hakolito), [Hector Minaya](https://www.facebook.com/HectorMinaya), [Orly Pereyra](https://www.facebook.com/orlypereyra), [Eury Fernando Vallejo Perez](https://www.facebook.com/EuryVallejo), Luis Jimenez y [Carlos Vásquez Polanco](https://www.facebook.com/carlosvasquez).

+ [developers X](https://www.facebook.com/groups/developers.x/)  
  - Administradores: [Jose Elias](https://www.facebook.com/eliax) y [Raydelto Hernandez](https://www.facebook.com/raydelto) 

+ [DevelopersRD](https://www.facebook.com/groups/developersrd/)  
  - Administrador: [Stanley Lara](https://www.facebook.com/stanley.lara)

+ [Hackers and Founders - Santo Domingo](https://www.facebook.com/groups/161328360736390/) 
  - Administradores: [Jorge Luis Vargas Pichardo](https://www.facebook.com/elpargo), [Luz Angelys González Hernández](https://www.facebook.com/luzgonzalezh) y  [Leonardo Antonio Jimenez Soriano](https://www.facebook.com/leonardoajim).

+ [Javascript Dominicana](https://www.facebook.com/groups/JavaScriptDominicana/) 
  - Administradores: [Efraín Reyes](https://www.facebook.com/Efrax86), [Luz Angelys González Hernández](https://www.facebook.com/luzgonzalezh), [Leonardo Antonio Jimenez Soriano](https://www.facebook.com/leonardoajim) y [Francis Brito](https://www.facebook.com/frxbr).

+ [Mobile Developer Group](https://www.facebook.com/groups/mobilenetSDQ/)
  - Administradores: [Hector Minaya](https://www.facebook.com/HectorMinaya), [Claudio Sanchez](https://www.facebook.com/claudio.sanchez.98031506), [Efraín Reyes](https://www.facebook.com/Efrax86), [Luz Angelys González Hernández](https://www.facebook.com/luzgonzalezh), [Ahmed Ayub](https://www.facebook.com/amhedh), [Noe Branagan](https://www.facebook.com/noe.branagan), [Luis Ramirez](https://www.facebook.com/luisyamille.ramirezcastillo), [Jose Luis Bencosme](https://www.facebook.com/joseluisbencosme), y [Emmanuel Ponciano](https://www.facebook.com/egladheim)  

+ [Python Dominicana](https://www.facebook.com/groups/pythondo/)
  - Administradores: [Jorge Luis Vargas Pichardo](https://www.facebook.com/elpargo), [Luz Angelys González Hernández](https://www.facebook.com/luzgonzalezh), [Leonardo Antonio Jimenez Soriano](https://www.facebook.com/leonardoajim) y [Jose Ramon De Los Santos](https://www.facebook.com/gjx201) 

##Interactividad 
 
En la página de inicio se muestra la actividad de los grupos, tanto las publicaciones creadas como las reacciones a estas. Se toma para la muestra la información de todo un año de actividad de los distintos grupos. 
 
La gráfica permite deshabilitar grupos y solo dejar aquellos que se quieran comparar. 
 
##Top 20 

Listado de los Developers más influyentes en un grupo y a nivel global  de todas las comunidades. 
 
Al ver el top 20 de un grupo en particular, se listan aquellos Developers que han tenido actividad en dicho grupo y que cumplen con la cuota mínima para entrar en el top. 

El top 20 general toma toda la información de todos los grupos y saca los 20 Developers con el rating más alto a nivel general, igual para entrar en este top se toma una cantidad mínima de interacciones cuyo algoritmo será explicado más adelante. 

##Rating  
Es el prorrateo que mide la aceptación de las interacciones del developer con el resto de la comunidad. Cada developer tiene un rating individual según la actividad que tenga en los distintos grupos y un rating global. 

El algoritmo utilizado para determinar el rating es el siguiente: 
 
IF( (pc + cc ) < mini, 0,  
       Cast((((cl*2) + pl + ((mpc+mmc) *2))  / (cc+pc)) AS DECIMAL(5, 1)))  
 
pc= Cantidad de publicaciones realizadas 
cc= Cantidad de comentarios realizadas 
mini= Mínimo de interacciones permitido por el grupo 
cl=Likes en comentarios obtenidos 
pl=Likes en publicaciones obtenidos 
mpc=Cantidad de menciones en publicaciones obtenidas 
mmc=Cantidad de menciones en comentarios obtenidas 

##Mínimo de interacciones en grupo 
La cantidad mínima de interacciones es calculada individualmente por grupo y es la determinante para considerar si un developer puede ser incluido dentro de su top 20.  
 
El algoritmo para determinar la cantidad mínima de interacciones es el siguiente: 
 
ROUND(( pcg + ccg )*0.4/100) 

pcg=Cantidad de publicaciones del grupo 
ccg=Cantidad de comentarios del grupo 

Expresa que grupos con mucha actividad tendrán una cantidad mínima de interacción mayor a la de por ejemplo un focus group. De este modo se tiene una cuota mínima justa que dependerá siempre del grupo y de la actividad dentro de el.                                                                                           
