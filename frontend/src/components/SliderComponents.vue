<template>
  <Swiper
    :modules="[Navigation, Pagination, Autoplay]"
    :slides-per-view="1"
    :loop="true"
    :autoplay="{ delay: 3500 }"
    navigation
    pagination
    class="slider"
  >
    <SwiperSlide v-for="(slide, index) in slides" :key="index">
      <img :src="slide.image" :alt="`Slide ${index + 1}`" class="slider-image" />
    </SwiperSlide>
  </Swiper>
</template>

<script setup>
import { Swiper, SwiperSlide } from 'swiper/vue'
import { Navigation, Pagination, Autoplay } from 'swiper/modules'
import 'swiper/css'
import 'swiper/css/navigation'
import 'swiper/css/pagination'

// Contoh data gambar slider
const slides = [
  { image: require('@/assets/slider1.jpg') },
  { image: require('@/assets/slider2.jpg') },
  { image: require('@/assets/slider3.jpg') }
]
</script>

<style scoped>
.slider {
  width: 100%;
  max-width: 1200px;
  margin: 0 auto;
  border-radius: 12px;
  overflow: hidden;
}

.slider-image {
  width: 100%;
  height: auto;
  object-fit: cover;
}
</style>
