import { createRouter, createWebHistory } from 'vue-router'
import Inicio from '../views/Inicio.vue'
import Tipos from '../views/Tipos.vue'
import Astros from '../views/Astros.vue'
import TiposForm from '../views/TiposForm.vue'
import AstrosForm from '../views/AstrosForm.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'inicio',
      component: Inicio
    },
    {
      path: '/tipos',
      name: 'tipos',
      component: Tipos
    },
    {
      path: '/astros',
      name: 'astros',
      component: Astros
    },
    {
      path: '/tiposForm',
      name: 'tiposForm',
      component: TiposForm
    },
    {
      path: '/astrosForm',
      name: 'astrosForm',
      component: AstrosForm
    }
  ]
})

export default router
