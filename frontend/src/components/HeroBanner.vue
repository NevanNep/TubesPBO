<template>
  <section class="hero-banner">
    <div class="banner-wrapper" :style="wrapperStyle">
      <div
        class="banner-image"
        v-for="(banner, index) in banners"
        :key="index"
        :style="{ width: bannerWidth }"
      >
        <img :src="banner" alt="Banner Image" />
      </div>
    </div>

    <button class="arrow left" @click="prev">&#10094;</button>
    <button class="arrow right" @click="next">&#10095;</button>
  </section>
</template>

<script>
export default {
  data() {
    return {
      currentIndex: 0,
      isTransitioning: false,
      banners: [
        require('@/assets/banner1.png'),
        require('@/assets/banner2.png'),
        require('@/assets/banner3.png'),
      ],
      autoSlideInterval: null,
      autoSlideDelay: 4000,
    }
  },
  computed: {
    wrapperStyle() {
      return {
        transform: `translateX(-${this.currentIndex * 100}%)`,
        transition: 'transform 0.5s ease-in-out',
        width: `${this.banners.length * 100}%`,
        display: 'flex',
      }
    },
    bannerWidth() {
      return `${100 / this.banners.length}%`
    }
  },
  methods: {
    next() {
      if (this.isTransitioning) return
      this.isTransitioning = true
      this.currentIndex = (this.currentIndex + 1) % this.banners.length
      this.unlockAfterTransition()
      this.resetAutoSlide()
    },
    prev() {
      if (this.isTransitioning) return
      this.isTransitioning = true
      this.currentIndex =
        (this.currentIndex - 1 + this.banners.length) % this.banners.length
      this.unlockAfterTransition()
      this.resetAutoSlide()
    },
    unlockAfterTransition() {
      setTimeout(() => {
        this.isTransitioning = false
      }, 500)
    },
    startAutoSlide() {
      this.autoSlideInterval = setInterval(() => {
        this.next()
      }, this.autoSlideDelay)
    },
    stopAutoSlide() {
      if (this.autoSlideInterval) {
        clearInterval(this.autoSlideInterval)
        this.autoSlideInterval = null
      }
    },
    resetAutoSlide() {
      this.stopAutoSlide()
      this.startAutoSlide()
    }
  },
  mounted() {
    this.startAutoSlide()
  },
  beforeUnmount() {
    this.stopAutoSlide()
  }
}
</script>

<style scoped>
.hero-banner {
  position: relative;
  width: 100%;
  overflow: hidden;
  margin: 0 auto;
  border-radius: 0;
}

.banner-wrapper {
  height: 450px;
  display: flex;
}

.banner-image {
  flex-shrink: 0;
  min-width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
}

.banner-image img {
  max-width: 100%;
  max-height: 100%;
  object-fit: contain;
  user-select: none;
  display: block;
}

/* Arrows */
.arrow {
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
  background: rgba(255, 255, 255, 0.7);
  color: #45000d;
  font-size: 2rem;
  border: none;
  border-radius: 50%;
  width: 3rem;
  height: 3rem;
  cursor: pointer;
  transition: background 0.3s ease;
  user-select: none;
  z-index: 10;
}

.arrow:hover {
  background: rgba(255, 255, 255, 1);
}

.arrow.left {
  left: 1rem;
}

.arrow.right {
  right: 1rem;
}

/* Responsive */
@media (max-width: 768px) {
  .banner-wrapper {
    height: 250px;
  }

  .arrow {
    font-size: 1.5rem;
    width: 2.5rem;
    height: 2.5rem;
  }
}
</style>
