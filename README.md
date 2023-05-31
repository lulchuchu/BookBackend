# BookBackend
## Description:
Book website help people order books easily<br/>
Users can view books, search book and filter suitable books<br/>
Users can add books to cart and checkout later, cant add to cart or check out number of books exceeding book's quantity in stock<br/>
Users can rate book star can leave comments to books<br/>
## Detail:
Using Spring boot to create project <br/>
Using Spring Data JPA to connect to MySQL database for fetching and updating data <br/>
Using Spring Security and JWT for User authentication and authorization <br/>
### Main page
![image](https://github.com/lulchuchu/BookBackend/assets/31687664/fb7b6d85-df67-4f75-b06a-ae729c3ad84e)
This is main page after user log in, this page display books info<br/>
There is a buttong list on the top, user can use it to find suitable books<br/>
There is a search bar in heading, when user type a string, it will find books that have title, author or category match the word user typed<br/>
![image](https://github.com/lulchuchu/BookBackend/assets/31687664/96047fcf-84ff-4f49-80b5-25f06569a903)
![image](https://github.com/lulchuchu/BookBackend/assets/31687664/87cbf6b7-b9c7-4856-aa00-2827de89c2b5)
This is the book details page displays all book's information such as: title, author, price, number left, ...<br/>
User can change the number of books and add to cart but can't add to cart when the number exceed the number left in stock.If that book already in cart, it will merge to 1 item in cart and increment the book's quantity in cart<br/>
User can leave a star then write a review about the book on the right side <br/>
### Cart page
![image](https://github.com/lulchuchu/BookBackend/assets/31687664/55168ef1-1ed8-478d-890b-85ac9460f71c)
This page display book shopping cart, User can see the item in cart's info and change number of books want to in this page. <br/>
User can checkout or delete this book from cart, if checkout the current book's quantity in stock will be subtract and sold will increment <br/>
### Checkout page
![image](https://github.com/lulchuchu/BookBackend/assets/31687664/c52ce9d4-96da-4dd5-8096-784e524e2b69)
This page display user's bought books, user can cancel buy this book and then this book's quantity will be add up to book's quantity in stock <br/>
### Admin page
![image](https://github.com/lulchuchu/BookBackend/assets/31687664/f834c61e-bdc2-46de-99d0-2b42a1e5f928)
This page will display all books for admin to manage, admin can add new book, delete book and update book information<br/>
![image](https://github.com/lulchuchu/BookBackend/assets/31687664/12924ae0-c80d-46e1-82b4-bea4f7bc8079)
This page will help admin to add new books, all field is required and will display error on top of input to show user error, new book must not have the same title and author with old book<br/>
![image](https://github.com/lulchuchu/BookBackend/assets/31687664/6be45534-284d-49ac-9001-1cba636947b8)
This page will help admin to update books information
