# Online Book Store

## Project Overview

This project aims to develop an Online Book Store application, implemented step by step. The app will feature several domain models/entities:

- **User**: Contains registered user information, including authentication details and personal information.
- **Role**: Represents user roles within the system, such as admin or regular user.
- **Book**: Represents books available in the store.
- **Category**: Represents book categories.
- **ShoppingCart**: Represents a user's shopping cart.
- **CartItem**: Represents items in a user's shopping cart.
- **Order**: Represents orders placed by users.
- **OrderItem**: Represents items in a user's order.

This is an overview of the project that will be implemented gradually throughout this module. The project will be divided into parts. Create a new GitHub repository for this project, which you can later share in your CV as proof of your work for potential interviewers.

## People Involved

### Shopper (User)
- Looks at books, adds them to the shopping cart, and makes purchases.

### Manager (Admin)
- Arranges books on the shelf and oversees purchases.

## Functionalities

### For Shoppers
- **Join and sign in**: Register and sign in to access books and make purchases.
- **View and search for books**: Browse books, search by name, view details.
- **Explore bookshelf sections**: Access book categories and view books within each section.
- **Use the shopping cart**: Add, view, and remove items.
- **Purchase books**: Buy items in the shopping cart.
- **View past receipts**: Review previous purchase details.

### For Managers
- **Arrange books**: Add, modify, or remove books from the store.
- **Organize bookshelf sections**: Create, update, or remove book categories.
- **Manage receipts**: Update receipt statuses (e.g., "Shipped" or "Delivered").

## Technologies Used
<details>
  <summary><img src="https://trellat.es/wp-content/uploads/spring-boot-logo-300x300.png" height="30" width="30"/> Spring Boot</summary>

`In this project, I used Spring Boot as the main framework for building the application.`
</details>

<details>
  <summary><img src="https://th.bing.com/th/id/R.de96e7f7a17f2057614a627531a45ef4?rik=6M2yfVh4aSx5wA&pid=ImgRaw&r=0" height="30" width="30"/> Java</summary>

`In my project, I choose Java as the main programming language.`
</details>

<details>
  <summary><img src="https://www.vectorlogo.zone/logos/hibernate/hibernate-ar21.png" height="30" width="30"/> Hibernate</summary>

`I utilized Hibernate as the ORM (Object-Relational Mapping) framework.`
</details>

<details>
  <summary><img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQwsq-7f5BWyog4cdeT1sQaYLVzhJ0o37Up8TjHvVU08WUgfyyMMRMHTVwJ5XReSjyhZa0&usqp=CAU" height="30" width="30"/> Spring Security</summary>

`I implemented with Spring Security to manage authentication and authorization.`
</details>

<details>
  <summary><img src="https://velog.velcdn.com/images/knavoid/post/a7f881b0-6a0c-4866-8b12-fffe9b3f37e0/image.png" height="30" width="30"/> Spring Data JPA</summary>

`In this project, I used Spring Data JPA for simplified data access and manipulation.`
</details>

<details>
  <summary><img src="https://th.bing.com/th/id/OIP.RJMiOW4RJ3d1o01vXnqEPAHaFj?rs=1&pid=ImgDetMain" height="30" width="30"/> Maven</summary>

`I relied on Maven for project management and build automation.`
</details>

# Database structure:
\
![scheme](structure/db_schema.png)

---

# Project controllers with the following endpoints:


---

## **Authentication Controller:**

| **HTTP method** | **Endpoint**  | **Role** | **Function** |
|:----------------:|:--------------:|:--------:|:-------------|
| POST | /register | ALL | Allows users to register a new account. |
| POST | /login | ALL | Get JWT tokens for authentication. |

---

## **Book Controller:** _Searching for books (CRUD for books)_

| **HTTP method** | **Endpoint**  | **Role** | **Function**                       |
|:---------------:|:-------------:|:--------:|:-----------------------------------|
|       GET       |    /books     |   USER   | Endpoints for managing books.      |
|       GET       |  /books/{id}  |   USER   | Search for a specific book by id.  |
|       PUT       | /{books}/{id} |  ADMIN   | Allows admin to update book by id. |
|      POST       |   /{books}    |  ADMIN   | Allows admin to create new book.   |
|     DELETE      | /{books}/{id} |  ADMIN   | Allows admin to delete book.       |

---

## **Category Controller:** _Managing category (CRUD for Categories)_

| **HTTP method** |   **Endpoint**    | **Role** | **Function**                          |
|:--------------:|:-----------------:|:--------:|:--------------------------------------|
|      POST      |     /category     |  ADMIN   | Allow admin to create a new category. |
|       GET      |     /category     |   USER   | Get all categories from DB.           |
|       GET      |  /category/{id}   |   USER   | Get category by id from DB.           |
|       PUT      |  /category/{id}   |  ADMIN   | Update category by id.                |
|    DELETE      |  /category/{id}   |  ADMIN   | Allow admin delete some category.     |

---

## **Cart Controller:** _User cart management_

| **HTTP method** |    **Endpoint**     | **Role** | **Function**                                   |
|:---------------:|:-------------------:|:--------:|:-----------------------------------------------|
|       GET       |        /cart        |   USER   | Get cart from logged user from DB.             |
|      POST       |        /cart        |   USER   | Add books to the user cart".                   |
|     DELETE      |  /cart-items/{id}   |   USER   | Delete cart item from the user cart.           |
|       PUT       |  /cart-items/{id}   |   USER   | Update quantity of cart item in the user cart. |

---

## **Order Controller:** _Managing orders_

| **HTTP method** |           **Endpoint**           | **Role** | **Function**                         |
|:--------------:|:--------------------------------:|:--------:|:-------------------------------------|
|      POST      |             /orders              |   USER   | Allow user to make new order.        |
|       PUT      |        /orders/{orderId}         |  ADMIN   | Allows admin to update order status. |
|       GET      |             /orders              |   USER   | Get order history.                   |
|       GET      | /orders/{orderId}/items/{itemId} |   USER   | Get item from order.                 |
|       GET      |     /orders/{orderId}/items      |   USER   | Get all items from order.            |

---

## Project Setup

To set up the project locally, follow these steps:
1. Clone the repository: `git clone https://github.com/don-bigdad/SpringBoot-Book-Shop.git`
2. Navigate to the project directory: `cd your_project`
3. Build the project using Maven: `mvn clean install`
4. Run the application: `mvn spring-boot:run`
## Contact

For additional information or inquiries, please contact:
- Email: bohdan.maksymenko.jv@gmail.com
- Telegram: https://t.me/Blgdad
- LinkedIn: [Your LinkedIn Profile](https://www.linkedin.com/in/your_profile)
