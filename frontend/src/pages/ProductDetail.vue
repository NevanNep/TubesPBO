<template>
  <div class="product-detail">
    <img :src="product.image" :alt="product.name" class="detail-image" />
    <h1>{{ product.name }}</h1>
    <h3>{{ product.brand }}</h3>
    <p>Price: Rp {{ product.price.toLocaleString('id-ID') }}</p>
    <p v-if="product.discount">Discount: {{ product.discount }}%</p>
    <p>Rating: {{ product.rating }} / 5</p>
  </div>
</template>

<script>
export default {
  data() {
    return {
      product: {}
    };
  },
  created() {
    const id = this.$route.params.id;
    
    // Sumber data bisa dari Vuex, API, atau props sementara
    const dummyData = [
      {
        id: 101,
        name: 'Yves Saint Laurent MYSLF EDP 100 ml',
        brand: 'YVES SAINT LAURENT',
        price: 2350000,
        discount: 25,
        rating: 4,
        image: require('@/assets/hitamm.jpeg'),
        category: 'Unisex'
      },
        {
          id: 102,
          brand: 'SAMPAR',
          name: 'Sampar Prodigal Pen 6 ml',
          price: 1400000,
          discount: 50,
          rating: 4,
          image: require('@/assets/parfumgay.png'),
          category: 'Women',
          isNew: false
        },
        {
          id: 103,
          brand: 'YVES ROCHER',
          name: 'Yves Rocher Anti Dandruff Shampoo 300 ml',
          price: 2390000,
          discount: 44,
          rating: 5,
          image: require('@/assets/parfumcewecakep.png'),
          category: 'Men',
          isNew: false
        },
        {
          id: 104,
          brand: 'ESSENTIAL PARFUMS',
          name: 'Essential Parfums Bois Imperial EDP 100 ml',
          price: 1990000,
          discount: 10,
          rating: 5,
          image: require('@/assets/toxic.png'),
          category: 'Unisex',
          isNew: false
        },
        {
          id: 105,
          brand: 'GIORGIO ARMANI',
          name: 'Emporio Armani Stronger With You Intensely EDP 100 ml',
          price: 1870000,
          discount: 0,
          rating: 5,
          image: require('@/assets/aviena.jpeg'),
          category: 'Men',
          isNew: false
        },
        {
          id: 106,
          brand: 'BOHEMIAN PARFUME',
          name: 'Bohemian Taste With You EDP 250 ml',
          price: 2400000,
          discount: 0,
          rating: 5,
          image: require('@/assets/bohemian.png'),
          category: 'Men',
          isNew: false
        },
        {
          id: 107,
          brand: 'GIORGIO ARMANI',
          name: 'Emporio Armani Stronger With You Intensely EDP 100 ml',
          price: 1870000,
          discount: 0,
          rating: 5,
          image: require('@/assets/aqua.jpeg'),
          category: 'Men',
          isNew: false
        },
        {
          id: 108,
          brand: 'GIORGIO ARMANI',
          name: 'Emporio Armani Stronger With You Intensely EDP 100 ml',
          price: 1870000,
          discount: 0,
          rating: 5,
          image: require('@/assets/merahmerona.png'),
          category: 'Women',
          isNew: false
        }
      
      // tambahkan produk lainnya...
    ];
    this.product = dummyData.find(p => p.id == id);
  }
}
</script>

<style scoped>
.product-detail {
  max-width: 600px;
  margin: 2rem auto;
  text-align: center;
}
.detail-image {
  width: 100%;
  max-height: 400px;
  object-fit: contain;
}
</style>
