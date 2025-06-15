<template>
  <aside class="w-full md:w-80 p-5 bg-white rounded-xl shadow border text-[#111] space-y-6">
    <!-- KATEGORI -->
    <div>
      <h3 class="text-sm font-bold uppercase tracking-wide mb-3">KATEGORI</h3>
      <ul class="space-y-2 text-sm list-none m-0 p-0">
        <li>
          <label class="flex items-center cursor-pointer">
            <input type="checkbox" class="mr-2" disabled />
            Eau de Parfum
          </label>
        </li>
        <li>
          <label class="flex items-center cursor-pointer">
            <input type="checkbox" class="mr-2" disabled />
            Eau de Toilette
          </label>
        </li>
        <li>
          <label class="flex items-center cursor-pointer">
            <input type="checkbox" class="mr-2" disabled />
            Extrait de Parfum
          </label>
        </li>
      </ul>
    </div>

    <!-- HARGA -->
    <div>
      <h3 class="text-sm font-bold uppercase tracking-wide mb-3">HARGA</h3>
      <input
        type="range"
        min="0"
        max="6500000"
        step="50000"
        v-model="price"
        class="w-full accent-red-600"
      />
      <p class="text-xs text-gray-600 mt-2">
        Price:<br /><strong>Rp 0 - Rp {{ price.toLocaleString() }}</strong>
      </p>
    </div>

<!-- BRANDS -->
<div>
  <h3 class="text-sm font-bold uppercase tracking-wide mb-3">BRANDS</h3>
  <div class="relative w-full max-w-full overflow-hidden">
    <input
      v-model="searchBrand"
      type="text"
      placeholder="Search Brands"
      class="block w-full border border-gray-300 px-3 pr-10 py-2 text-sm rounded-md focus:outline-none focus:ring-1 focus:ring-red-500"
    />
    <span class="absolute inset-y-0 right-3 flex items-center pointer-events-none">
      <i class="fa fa-search text-gray-400 text-sm"></i>
    </span>
  </div>
  <div class="mt-3 max-h-40 overflow-y-auto pr-1 space-y-1 text-sm">
    <label
      v-for="brand in filteredBrands"
      :key="brand"
      class="flex items-center cursor-pointer"
    >
      <input type="checkbox" v-model="selectedBrands" :value="brand" class="mr-2" />
      {{ brand }}
    </label>
  </div>
</div>


    <!-- APPLY BUTTON -->
    <button
      @click="applyFilters"
      class="w-full bg-black text-white text-sm font-medium py-2 rounded hover:bg-red-700 transition"
    >
      Terapkan Filter
    </button>
  </aside>
</template>

<script>
export default {
  name: 'FilterSidebar',
  props: {
    brands: {
      type: Array,
      default: () => []
    }
  },
  data() {
    return {
      selectedBrands: [],
      price: 3139000,
      searchBrand: ''
    }
  },
  computed: {
    filteredBrands() {
      return this.brands.filter(brand =>
        brand.toLowerCase().includes(this.searchBrand.toLowerCase())
      )
    }
  },
  methods: {
    applyFilters() {
      this.$emit('filter', {
        brands: this.selectedBrands,
        maxPrice: this.price
      })
    }
  }
}
</script>

<style scoped>
::-webkit-scrollbar {
  width: 5px;
}
::-webkit-scrollbar-thumb {
  background-color: #ccc;
  border-radius: 10px;
}
</style>
