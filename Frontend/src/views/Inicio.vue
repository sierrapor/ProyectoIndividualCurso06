<template>
  <main class="inicio">
    <div class="content">
      <h1 class="welcome-message">Bienvenido al Catálogo de Objetos Astronómicos</h1>
    </div>
  </main>
</template>

<style scoped>
.inicio {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh; /* Asegura que el contenedor ocupe toda la altura de la ventana */
  background-image: url('@/assets/nebulosaCarina.jpg'); /* Ruta a la imagen de fondo */
  background-size: cover; /* Asegura que la imagen cubra todo el fondo */
  background-position: center; /* Centra la imagen de fondo */
  background-repeat: no-repeat; /* Evita que la imagen se repita */
}

.content {
  text-align: center; /* Centra el texto dentro del contenedor */
  color: white; /* Asegura que el texto sea legible sobre la imagen de fondo */
}

.welcome-message {
  font-size: 2em; /* Ajusta el tamaño de la fuente según tus necesidades */
}
</style>