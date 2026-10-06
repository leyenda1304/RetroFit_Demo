package udeg.cuvalles.retrofit_demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import udeg.cuvalles.retrofit_demo.data.Post
import udeg.cuvalles.retrofit_demo.data.RetrofitInstance
import udeg.cuvalles.retrofit_demo.ui.theme.RetroFit_DemoTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            RetroFit_DemoTheme {
                PantallaPublicaciones()
            }
        }
    }
}

@OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
@Composable
fun PantallaPublicaciones() {

    var publicaciones by remember {
        mutableStateOf<List<Post>>(emptyList())
    }

    var cargando by remember {
        mutableStateOf(true)
    }

    var mensajeError by remember {
        mutableStateOf("")
    }

    LaunchedEffect(Unit) {

        try {
            publicaciones = RetrofitInstance.api.getPosts()
        } catch (e: Exception) {
            mensajeError = "No se pudieron cargar las publicaciones."
        } finally {
            cargando = false
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Lista de publicaciones")
                }
            )
        }
    ) { valoresPadding ->

        when {

            cargando -> {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(valoresPadding),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {

                    CircularProgressIndicator()

                    Text(
                        text = "Cargando publicaciones...",
                        modifier = Modifier.padding(top = 16.dp)
                    )
                }
            }

            mensajeError.isNotEmpty() -> {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(valoresPadding),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {

                    Text(
                        text = mensajeError,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }

            else -> {

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(valoresPadding),
                    contentPadding = PaddingValues(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    items(publicaciones) { publicacion ->

                        TarjetaPublicacion(publicacion)
                    }
                }
            }
        }
    }
}

@Composable
fun TarjetaPublicacion(publicacion: Post) {

    val tituloEspanol = when (publicacion.id) {

        1 -> "Realización de una actividad"
        2 -> "Organización de información"
        3 -> "Trabajo y aprendizaje"
        4 -> "Desarrollo de un proyecto"
        5 -> "Importancia de la comunicación"
        6 -> "Planeación de actividades"
        7 -> "Uso de nuevas tecnologías"
        8 -> "Aprendizaje mediante proyectos"
        9 -> "Mejora de procesos"
        10 -> "Trabajo en equipo"

        else -> "Información de la publicación"
    }

    val descripcionEspanol = when (publicacion.id) {

        1 -> "Esta publicación muestra información obtenida desde una API mediante una conexión a Internet."

        2 -> "Los datos fueron recibidos correctamente utilizando una solicitud GET con Retrofit."

        3 -> "Esta información forma parte de los datos obtenidos desde el servicio web."

        4 -> "La aplicación consulta información externa y la muestra de forma ordenada."

        5 -> "Los datos se presentan dentro de una lista que permite desplazarse fácilmente."

        6 -> "La aplicación utiliza Retrofit para realizar la conexión con el servicio web."

        7 -> "Esta publicación representa información obtenida desde una fuente externa."

        8 -> "Los datos fueron cargados automáticamente desde la API."

        9 -> "La información recibida se muestra dentro de una tarjeta en la aplicación."

        10 -> "La aplicación permite visualizar diferentes publicaciones obtenidas desde Internet."

        else -> "Información obtenida mediante una conexión a una API utilizando Retrofit."
    }

    Card(
        modifier = Modifier.fillMaxWidth()
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Text(
                text = "Publicación #${publicacion.id}",
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = "Usuario: ${publicacion.userId}",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 4.dp)
            )

            Text(
                text = "Título: $tituloEspanol",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(top = 8.dp)
            )

            Text(
                text = descripcionEspanol,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

