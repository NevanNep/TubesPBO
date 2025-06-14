<template>
  <section id="shop-by-categories" class="shop-by-categories">
    <div class="category-cards">
      <router-link to="/category/men" class="category-card">
        <img src="@/assets/men2.jpg" alt="Men Category" />
        <span>Men</span>
      </router-link>
      <router-link to="/category/women" class="category-card">
        <img src="@/assets/women2.jpg" alt="Women Category" />
        <span>Women</span>
      </router-link>
      <router-link to="/category/unisex" class="category-card">
        <img src="@/assets/couple1.jpg" alt="Unisex Category" />
        <span>Unisex</span>
      </router-link>
    </div>
  </section>
</template>

<style scoped>
.shop-by-categories {
  padding: 40px 20px;
  text-align: center;
  font-family: "Poppins", sans-serif;
}
.category-cards {
  display: flex;
  justify-content: center;
  gap: 24px;
  flex-wrap: wrap;
}

.category-card {
  width: 350px;
  cursor: pointer;
  text-decoration: none;
  color: #45000D;
  display: flex;
  flex-direction: column;
  align-items: center;
  transition: transform 0.3s ease, box-shadow 0.3s ease;
  border-radius: 8px;
  overflow: hidden;
  box-shadow: 0 4px 12px rgba(0,0,0,0.15);
}

.category-card img {
  width: 100%;
  height: 400px;
  object-fit: cover;
  border-radius: 12px 12px 0 0;
  transition: transform 0.3s ease;
}

.category-card span {
  margin-top: 12px;
  font-size: 1.2rem;
  font-weight: 600;
  user-select: none;
}

.category-card:hover {
  transform: scale(1.05);
  box-shadow: 0 6px 18px rgba(0,0,0,0.25);
  color: #700014;
}

.category-card:hover img {
  transform: scale(1.0);
}
</style>
