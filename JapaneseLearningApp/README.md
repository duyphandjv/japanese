# tekotestrepo
# Techniques

- Programming language: Kotlin
- Architecture: MVVM
- Api library: Retrofit
- DB: Room

# Approach

- Design base object model for product list to improve performance (we can not caching because this information is modified frequently
- Design full object model for product detail, design product detail entity for detail information to insert to DB for the caching so we can improve the performance (this information is rarely modified for a product id)
- We need to control the number of products which are added to cart by user => design product cart entity and insert to DB when user wants to add to cart

- For auto completely seaching function, do the api requet after user input some characters with a delay time
- For the UI flow, chosing navigation support and data binding

# Missing
- Not implement caching DB for product detail information yet
- Not implement "watch more" function in detail screen yet