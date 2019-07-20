# TMDB_app

_Aplicación para consumir API de TMDB_

## Respuesta a preguntas:

1) Principio de responsabilidad única: Es el primero de los principios SOLID que buscan crear una metodología para generar código limpio y sostenible. Específicamente, el principio de responsabilidad única busca asignar a cada clase una única función o responsabilidad dentro del proyecto.

2) Desde mi punto de vista un código limpio es aquel que además de ser fácil de interpretar tiene características como:
		- Estar estructurado mediante funciones o módulos, y en el mejor de los casos cumplir con el principio de responsabilidad única.
		- Los nombres de las variables deben ser fáciles de entender.
		- Debe ser ordenando en términos visuales (Indentación, comentarios).
		- Tener una lógica adecuada

## Funcionamiento:

Para el desarrollo de este proyecto se ha utilizado la arquitectura sugerida por Android en:

[Guía de arquitectura de apps](https://developer.android.com/jetpack/docs/guide?hl=es-419)
	
Esta arquitectura está basada en el patrón de diseño MVVM.
A continuación se explica el contenido del proyecto en Android usando JAVA

### Activities:

- App: Esta clase se extiende de Application y se usa para inyectar parámetros usando la librería Dagger 2.

- MenuPrincipal: Actividad principal del proyecto la cual aparece después del splash y permite seleccionar entre películas o series

- SearchActivity: Actividad que muestra las diferentes películas o series, además tiene la barra de búsqueda

- MovieVisor: Actividad que contiene los detalles de cada película

- TrailerVisor: Actividad que esta conectada con la API de youtube y permite ver los videos de los trailer(Sólo para películas)

## Adapters:

- CategoryAdapter: Se utiliza para mostrar las opciones de categorias de series o de películas: popular, top, upcoming

- MoviesAdapter: Muestra las películas (popular, top o upcoming) en el SearchActivity

- SeriesAdapter: Muestra las series (popular, top) en el SearchActivity

- MultiContentAdapter: Muestra los resultados de la barra de búsqueda (Pueden ser películas o series)

- viewPagerAdapter: Adaptador para mostrar las pestañas películas o series (En la actividad MenuPrincipal)
	

## Extended_libraries:
	
- sliderLayoutNoSwipe: Se utilizó una librería para mostrar las imágenes en las opciones de categorias popular, top o upcoming, sin embargo,
se extendió esta librería para anular el gesto de cambiar las imágenes, debido a que interfiere en la experiencia del usuario.
	
## Fragments:
- MoviesFragment: Pestaña del tablayout en el MenuPrincipal
- SearchFRagment: Pestaña del tablayout
	

## Carpeta Holders: Esta carpeta contiene los holder para los adaptadores
- HolderCategory: Holder del CategoryAdapter
- HolderMovies: Holder usado para el MoviesAdapter o SerieAdapter
- HolderMulticontent: Holder para el adapter del buscador(MultiContentAdapter)

## APIconnections: Interfaces para la conexión con la API de TMDB
- GenresWS: Se obtiene los géneros de series o de películas
- TMDBmovieWS: Se obtienen las películas por categoria (popular, top o upcoming) o también por palabra clave (Necesario para el buscador)
- TMDBserieWS: Se obtienen las series por categoria (popular o top) o también por palabra clave (Necesario para el buscador)
- TrailersWS: Se obtienen los trailers mediante la llave del video (Sólo para YouTube)

## Business (ViewModels): Comunicación entre los repositorios y actividad SearchActivity
- SearchActivityViewModel: Trae la información desde los repositorios (MoviesRepo o SeriesRepo), los cuales están conectados con la base de datos local que usa la librería de persistencia Room, las series o películas llegan a través de 4 variables (movies, moviesByQuery, series, seriesByQuery)
	

## Injection: Contiene las clases usadas en la inyección de dependencias en los repositorios
- RetrofitModule: Crea las dependencias a inyectar
- RetrofitComponent: Indica las clases a inyectar

## Utilities:
- Constants: Archivo con las constantes (path base de TMDB, claves de APIs, etc)

## Local Data (Capa de persistencia): Archivos para la configuración de la base de datos
### RoomEntities: Elementos que declaran las tablas en la base de datos local
- MoviesEntity: Tabla de películas
- SeriesEntity: Tabla de series
- GenresEntity: Tabla para almacenar los géneros de películas o series
### RoomDatabases: Declaración bases de datos locales
- MoviesDatabase: Contiene 2 tablas (MoviesEntity y GenresEntity)
- SeriesDatabase: Contiene 2 tablas (SeriesEntity y GenresEntity)
### RoomDAO: Métodos de almacenamiento o llamada de tablas en SQL
- MoviesDAO: Métodos para almacenar o llamar películas
- SeriesDAO: Métodos para alamcenar o llamar series
### RoomConverters: Se usan para almacenar datos complejos en SQL; como por ejemplo las listas de géneros y de paises
	
## PojoClasses: 

- Archivos POJO para dar formato a las consultas en TMDB

## Repositories:
- MoviesRepo: Métodos para refrescar la base de datos y buscar en la base de datos local de películas
- SeriesRepo: Métodos para refrescar la base de datos y buscar en la base de datos local de series




