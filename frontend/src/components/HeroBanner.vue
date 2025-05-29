<template>
  <section class="hero-banner">
    <div class="banner-wrapper" :style="wrapperStyle">
      <div
        class="banner-image"
        v-for="(banner, index) in banners"
        :key="index"
        :style="getBannerStyle(banner)"
      ></div>
    </div>

    <button class="arrow left" @click="prev">&#10094;</button>
    <button class="arrow right" @click="next">&#10095;</button>

    <!-- Tambahan dots navigation -->
    <div class="dots">
      <span
        v-for="(banner, index) in banners"
        :key="index"
        class="dot"
        :class="{ active: index === currentIndex }"
        @click="goToSlide(index)"
      ></span>
    </div>
  </section>
</template>

<script>
export default {
  data() {
    return {
      currentIndex: 0,
      banners: [
        require('@/assets/banner1.png'),
        require('@/assets/banner5.jpeg'),
        require('@/assets/banner12.png'),
      ],
      autoSlideInterval: null,
      autoSlideDelay: 4000,
    }
  },
  computed: {
    wrapperStyle() {
      return {
        width: `${this.banners.length * 100}vw`,
        transform: `translateX(-${this.currentIndex * 100}vw)`,
        transition: 'transform 0.5s ease-in-out',
        display: 'flex',
      }
    }
  },
  methods: {
    getBannerStyle(banner) {
      return {
        width: '100vw',
        height: '450px',
        backgroundImage: `url(${banner})`,
        backgroundSize: 'cover',
        backgroundPosition: 'center center',
        backgroundRepeat: 'no-repeat',
        flexShrink: 0,
      }
    },
    next() {
      this.currentIndex = (this.currentIndex + 1) % this.banners.length
      this.resetAutoSlide()
    },
    prev() {
      this.currentIndex =
        (this.currentIndex - 1 + this.banners.length) % this.banners.length
      this.resetAutoSlide()
    },
    goToSlide(index) {
      this.currentIndex = index
      this.resetAutoSlide()
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
  max-width: 1500px;
  width: 100%;
  margin: 0 auto;
  overflow: hidden;
  position: relative;
  height: 450px;
}

.banner-wrapper {
  height: 100%;
  display: flex;
}

.banner-image {
  flex-shrink: 0;
  box-sizing: border-box;
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

/* Dots navigation */
.dots {
  position: absolute;
  bottom: 1rem;
  width: 100%;
  display: flex;
  justify-content: center;
  gap: 0.75rem;
  z-index: 10;
}

.dot {
  width: 12px;
  height: 12px;
  background: rgba(255, 255, 255, 0.6);
  border-radius: 50%;
  cursor: pointer;
  transition: background 0.3s ease;
  border: 1.5px solid rgba(69, 0, 13, 0.6);
}

.dot:hover {
  background: rgba(255, 255, 255, 0.9);
}

.dot.active {
  background: #45000d;
  border-color: #45000d;
}

/* Responsive */
@media (max-width: 768px) {
  .hero-banner {
    height: 250px;
  }

  .banner-image {
    height: 250px !important;
  }

  .arrow {
    font-size: 1.5rem;
    width: 2.5rem;
    height: 2.5rem;
  }

  .dot {
    width: 10px;
    height: 10px;
  }
}
</style>
