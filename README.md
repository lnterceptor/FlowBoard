# Flow board application

Full stack kanban task management application built with **Spring Boot** and **Angular**

## Prerequisites

Before running the application locally, make sure you have installed:
- Docker
- Docker Compose

## Build and start the application:

```bash
docker compose up -d --build
```

- After startup Frontend (Angular) is available at http://127.0.0.1:4200 (CORS is configured for http://localhost:4200 and http://127.0.0.1:4200)

- Backend API is available at http://127.0.0.1:8080

### Seeded demo accounts (flyway)
- User 1: john.doe@email.com / John123!
- User 2: emma.johnson@email.com / Alex1234!
- User 3: emily.johnson@email.com / 12345


## Key features

- Boards, columns and cards CRUD operations, with column and card drag-and-drop movement with index clamping, card assignees, board-scoped tags, and due dates.
- Comments and permissions: Comment threads per card with permission checks enforced on backend.
- Authentication: Stateless JWT authentication (Authorization: Bearer <token>).
- Real-Time Sync: WebSocket for live updates on card, column, comment, and board-deletion events.
- Database and Testing: Schema versioning and seed data via Flyway; service-layer unit tests with JUnit 5 and Mockito.


## Endpoints

Note: All endpoints except for /api/auth/login, /api/auth/register and /ws/** require a valid JWT token.

### Authorisation
- Post api/auth/register - create new account
- Post api/auth/login - login into existing account


### For managing board

- Post /api/boards/ - create new board
- Get /api/boards/{userId} - get boards which specified user is the owner of
- Get /api/boards/{userId}/getJoinedBoards - get boards which specified user joined
- Delete /api/boards/{boardId} - delete specified board
- Get /api/boards/board/{boardId} - get specified board
- Patch /api/boards/{boardId} - update specified board
- Post /api/boards/addCollaborator - adds collaborator to specified board
- Delete /api/boards/removeCollabolator - removes specified collaborator from board


### For managing columns

- Post /api/columns/ - create new column
- Delete /api/columns/{columnId} - delete specified column
- Patch /api/columns/{columnId} - update specified column
- Patch /api/columns/reorder - change order in which columns are presented on board
- Get /api/columns/{boardId} - get columns for specified board


### For managing cards

- Post /api/cards/ - create new card
- Patch /api/cards/{cardId} - update specified card
- Patch /api/cards/reorder - change order in which cards are presented inside of a column
- Patch /api/cards/moveCard - move card between different columns
- Delete /api/cards/{cardId} - delete specified card
- Get /api/cards/{columnId} - get cards for belonging to specified column


### For managing comments 

- Get /api/cards/{cardId}/comments/ - get comments belonging to specified card
- Post /api/cards/{cardId}/comments/ - add new comment
- Delete /api/cards/{cardId}/comments/{commentId} - delete specified comment
- Patch /api/cards/{cardId}/comments/{commentId} - update specified comment


### For managing labels

- Post /api/boards/{boardId}/labels/ - add new label
- Delete /api/boards/{boardId}/labels/ - delete specified label
- Get /api/boards/{boardId}/labels/ - get available labels


### For managing users

- Get /api/users/ - get all users
- Delete /api/users/{userId} - delete specified user
- Patch /api/users/updateUser/{userId} - update email/username of specified user
- Patch /api/users/changePassword/{userId} - update password of specified user

