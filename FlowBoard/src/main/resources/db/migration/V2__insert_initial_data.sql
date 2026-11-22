INSERT INTO users (id, username, email, password, created_at, updated_at) VALUES (1,'John Doe', 'john.doe@email.com', '$2a$10$d2l0k444PAbRt0tGUhybye.vmXk8ip7apJozgYA5N2/5UzZZoy6V6', '2025-01-12 16:33:57', NULL);
INSERT INTO users (id, username, email, password, created_at, updated_at) VALUES (2, 'Mike Davis', 'mike.davis@email.com', '$2a$10$ZYWz47GeGl8qN035dK/WxuhUgWi5gNa7zOI6iGxGMB9Y1W.jnrswi', '2024-03-07 10:31:53', NULL);
INSERT INTO users (id, username, email, password, created_at, updated_at) VALUES (3, 'Emma Johnson', 'emma.johnson@email.com', '$2a$10$ThHWmajsyF7dTjhbAXLecuoSXbcOzJdBUP3QEFI8Kwwc7ei0hHzaS', '2024-12-14 09:35:00', NULL);
INSERT INTO users (id, username, email, password, created_at, updated_at) VALUES (4, 'Alex Smith', 'alex.smith@email.com', '$2a$10$XOdUcYmlN0xBncNpSulJ0OtNmsV6Gnl0kRFuJJJY1jAlmZCMoiYPq', '2023-04-15 15:35:31', NULL);
INSERT INTO users (id, username, email, password, created_at, updated_at) VALUES (5, 'Sarah Miller', 'sarah.miller@email.com', '$2a$10$rTrUrt6G4u2XAuNFuL46.e52bKJds2G2brgO7sXgkkqQiUfgo3UtG', '2026-06-28 22:39:43', NULL);
INSERT INTO users (id, username, email, password, created_at, updated_at) VALUES
                                                                              (6, 'John Smith', 'john.smith@email.com', '$2a$10$W0eKDldxEt9tQora1ECIkeftPTzMf.4uEJ3j/Aghoc9vSkp6FaWu6', '2025-07-12 14:25:31', NULL),
                                                                              (7, 'Emily Johnson', 'emily.johnson@email.com', '$2a$10$W0eKDldxEt9tQora1ECIkeftPTzMf.4uEJ3j/Aghoc9vSkp6FaWu6', '2025-08-03 09:17:42', NULL),
                                                                              (8, 'Michael Brown', 'michael.brown@email.com', '$2a$10$W0eKDldxEt9tQora1ECIkeftPTzMf.4uEJ3j/Aghoc9vSkp6FaWu6', '2025-10-19 16:48:12', NULL),
                                                                              (9, 'Olivia Davis', 'olivia.davis@email.com', '$2a$10$W0eKDldxEt9tQora1ECIkeftPTzMf.4uEJ3j/Aghoc9vSkp6FaWu6', '2025-12-05 11:32:54', NULL),
                                                                              (10, 'James Wilson', 'james.wilson@email.com', '$2a$10$W0eKDldxEt9tQora1ECIkeftPTzMf.4uEJ3j/Aghoc9vSkp6FaWu6', '2026-01-15 18:21:09', NULL);


insert into Board (id, board_name, description, creation_date, owner_id) values (1, 'Website Development','Track frontend, backend, testing, and deployment tasks.','2026-01-12 10:30:00', 1);
insert into Board (id, board_name, description, creation_date, owner_id) values (2, 'Mobile App Development','Organize mobile application features, development, and testing.','2026-06-15 08:45:00', 1);
insert into Board (id, board_name, description, creation_date, owner_id) values (3, 'Marketing Campaign','Manage campaign planning, content creation, and publication.','2026-01-20 11:15:00', 2);
insert into Board (id, board_name, description, creation_date, owner_id) values (4, 'Product Development','Coordinate product research, feature development, and releases.','2026-03-15 08:45:00', 3);
insert into Board (id, board_name, description, creation_date, owner_id) values (5, 'Content Creation','Organize article writing, editing, approval, and publishing.','2026-02-05 13:00:00', 4);

insert into collaborators (board_id, user_id) VALUES (1, 1);
insert into collaborators (board_id, user_id) VALUES (2, 1);
insert into collaborators (board_id, user_id) VALUES (3, 2);
insert into collaborators (board_id, user_id) VALUES (4, 3);
insert into collaborators (board_id, user_id) VALUES (5, 4);
insert into collaborators (board_id, user_id) VALUES (1, 3);
insert into collaborators (board_id, user_id) VALUES (2, 3);
insert into collaborators (board_id, user_id) VALUES (3, 1);
insert into collaborators (board_id, user_id) VALUES (4, 1);
INSERT INTO collaborators (board_id, user_id) VALUES
                                                  (1, 6),
                                                  (2, 7),
                                                  (1, 7),
                                                  (3, 8),
                                                  (1, 8),
                                                  (4, 9),
                                                  (1, 9),
                                                  (2, 9),
                                                  (5, 9),
                                                  (5, 10);

insert into columns (id, title, position_in_board, creation_date, board_name) VALUES (1, 'Planning', 0, '2026-02-12 10:30:00', 1);
insert into columns (id, title, position_in_board, creation_date, board_name) VALUES (2, 'Design', 1, '2026-02-12 10:30:40', 1);
insert into columns (id, title, position_in_board, creation_date, board_name) VALUES (3, 'Setup', 2, '2026-02-12 10:31:00', 1);
insert into columns (id, title, position_in_board, creation_date, board_name) VALUES (4, 'Development', 3, '2026-02-12 10:31:13', 1);
insert into columns (id, title, position_in_board, creation_date, board_name) VALUES (5, 'Testing', 4, '2026-02-12 10:31:25', 1);
insert into columns (id, title, position_in_board, creation_date, board_name) VALUES (6, 'Deployment', 5, '2026-02-12 10:32:13', 1);
insert into columns (id, title, position_in_board, creation_date, board_name) VALUES (31, 'Maintenance', 6, '2026-02-14 10:33:52', 1);
insert into columns (id, title, position_in_board, creation_date, board_name) VALUES (32, 'On Hold', 7, '2026-02-14 10:33:52', 1);

INSERT INTO columns (id, title, position_in_board, creation_date, board_name) VALUES
                                                                                  (7, 'Ideas', 0, '2026-03-12 10:32:13', 2),
                                                                                  (8, 'To Do', 1, '2026-04-18 14:22:31', 2),
                                                                                  (9, 'In Progress', 2, '2026-06-03 09:15:42', 2),
                                                                                  (10, 'Code Review', 3, '2026-07-21 16:45:18', 2),
                                                                                  (11, 'Testing', 4, '2026-09-14 11:08:55', 2),
                                                                                  (12, 'Released', 5, '2026-10-29 13:37:26', 2);

INSERT INTO columns (id, title, position_in_board, creation_date, board_name) VALUES
                                                                                  (13, 'Ideas', 0, '2026-02-08 09:14:22', 3),
                                                                                  (14, 'Planning', 1, '2026-03-19 11:32:45', 3),
                                                                                  (15, 'Content Creation', 2, '2026-05-07 14:18:09', 3),
                                                                                  (16, 'Review', 3, '2026-07-12 10:45:33', 3),
                                                                                  (17, 'Scheduled', 4, '2026-09-23 16:27:51', 3),
                                                                                  (18, 'Published', 5, '2026-11-05 13:09:16', 3);

INSERT INTO columns (id, title, position_in_board, creation_date, board_name) VALUES
                                                                                  (19, 'Research', 0, '2026-01-15 09:22:14', 4),
                                                                                  (20, 'Planning', 1, '2026-03-08 11:45:32', 4),
                                                                                  (21, 'Development', 2, '2026-04-26 14:18:09', 4),
                                                                                  (22, 'Testing', 3, '2026-07-13 10:37:51', 4),
                                                                                  (23, 'Launch Preparation', 4, '2026-09-19 16:12:43', 4),
                                                                                  (24, 'Released', 5, '2026-11-02 13:56:27', 4);

INSERT INTO columns (id, title, position_in_board, creation_date, board_name) VALUES
                                                                                  (25, 'Ideas', 0, '2026-02-11 08:42:19', 5),
                                                                                  (26, 'Writing', 1, '2026-03-24 10:15:36', 5),
                                                                                  (27, 'Editing', 2, '2026-05-16 13:28:42', 5),
                                                                                  (28, 'Review', 3, '2026-07-09 09:51:17', 5),
                                                                                  (29, 'Approved', 4, '2026-09-18 15:34:28', 5),
                                                                                  (30, 'Published', 5, '2026-11-06 12:07:53', 5);


INSERT INTO cards (id, title, completed, description, due_date, position_in_column, creation_date, column_id) VALUES
                                                                                                                  (1, 'Research Project Requirements', TRUE, 'Gather and document the initial requirements for the website project.', '2026-10-19', 0, '2026-11-17', 1),
                                                                                                                  (2, 'Define Website Goals', TRUE, 'Establish the primary goals, target audience, and expected outcomes.', '2026-10-20', 1, '2026-11-17', 1),
                                                                                                                  (3, 'Analyze Competitors', TRUE, 'Review competitor websites and identify opportunities for improvement.', '2026-10-21', 2, '2026-11-17', 1),
                                                                                                                  (4, 'Create Project Roadmap', TRUE, 'Prepare a development roadmap with milestones and deadlines.', '2026-10-22', 3, '2026-11-17', 1),
                                                                                                                  (5, 'Choose Technology Stack', FALSE, 'Evaluate and select the frontend, backend, and database technologies.', '2026-10-23', 4, '2026-11-17', 1),

                                                                                                                  (6, 'Create Wireframes', FALSE, 'Design initial wireframes for the homepage and main website sections.', '2026-10-24', 0, '2026-11-17', 2),
                                                                                                                  (7, 'Design Homepage Layout', TRUE, 'Create a visual layout for the website homepage.', '2026-10-25', 1, '2026-11-17', 2),
                                                                                                                  (8, 'Design Navigation Menu', FALSE, 'Prepare navigation structure and responsive menu designs.', '2026-10-26', 2, '2026-11-17', 2),
                                                                                                                  (9, 'Create UI Component Library', FALSE, 'Define reusable buttons, forms, cards, and other interface elements.', '2026-10-27', 3, '2026-11-17', 2),

                                                                                                                  (10, 'Set Up Development Environment', FALSE, 'Configure the repository, dependencies, and local development environment.', '2026-10-28', 0, '2026-11-17', 3),
                                                                                                                  (11, 'Initialize Frontend Application', TRUE, 'Create the frontend application structure and configure routing.', '2026-10-29', 1, '2026-11-17', 3),
                                                                                                                  (12, 'Set Up Backend API', TRUE, 'Initialize the backend application and configure API endpoints.', '2026-11-30', 2, '2026-11-17', 3),
                                                                                                                  (13, 'Configure Database', FALSE, 'Create database schemas and establish database connectivity.', '2026-12-01', 3, '2026-11-17', 3),
                                                                                                                  (14, 'Implement User Authentication', FALSE, 'Develop registration, login, and logout functionality.', '2026-12-02', 4, '2026-11-17', 3),

                                                                                                                  (15, 'Develop Homepage', FALSE, 'Implement the homepage according to the approved design.', '2026-12-03', 0, '2026-11-17', 4),
                                                                                                                  (16, 'Develop About Page', FALSE, 'Build the About Us page with company information and team details.', '2026-12-04', 1, '2026-11-17', 4),
                                                                                                                  (17, 'Develop Contact Form', FALSE, 'Create a contact form with client-side and server-side validation.', '2026-12-05', 2, '2026-11-17', 4),
                                                                                                                  (18, 'Implement Responsive Design', FALSE, 'Ensure all website pages work correctly on mobile, tablet, and desktop.', '2026-12-06', 3, '2026-11-17', 4),
                                                                                                                  (19, 'Integrate Backend API', FALSE, 'Connect frontend components with backend services and database operations.', '2026-12-07', 4, '2026-11-17', 4),

                                                                                                                  (20, 'Test User Registration', FALSE, 'Verify registration functionality, validation, and error handling.', '2026-12-08', 0, '2026-11-17', 5),
                                                                                                                  (21, 'Perform Cross-Browser Testing', FALSE, 'Test website functionality and appearance across major browsers.', '2026-12-09', 1, '2026-11-17', 5),
                                                                                                                  (22, 'Optimize Website Performance', FALSE, 'Improve loading speed, optimize images, and reduce unnecessary scripts.', '2026-12-10', 2, '2026-11-17', 5),
                                                                                                                  (23, 'Fix Reported Bugs', FALSE, 'Identify and resolve bugs discovered during testing.', '2026-12-11', 3, '2026-11-17', 5),

                                                                                                                  (24, 'Deploy Website to Production', FALSE, 'Configure the production environment and publish the website.', '2026-12-12', 0, '2026-11-17', 6),
                                                                                                                  (25, 'Final Website Review', FALSE, 'Perform a final review of the deployed website and verify all features.', '2026-12-13', 1, '2026-11-17', 6);


INSERT INTO cards (id, title, completed, description, due_date, position_in_column, creation_date, column_id) VALUES
                                                                                                                  (26, 'Define App Concept', TRUE, 'Establish the main purpose, target audience, and core features of the mobile application.', '2026-10-19', 0, '2026-11-17', 7),
                                                                                                                  (27, 'Research Target Audience', TRUE, 'Identify user needs, preferences, and expectations for the mobile app.', '2026-11-20', 1, '2026-11-17', 7),
                                                                                                                  (28, 'Explore Competitor Apps', TRUE, 'Analyze similar mobile applications and identify potential improvements.', '2026-10-21', 2, '2026-11-17', 7),
                                                                                                                  (29, 'Plan App Monetization', FALSE, 'Evaluate subscription, advertising, and in-app purchase opportunities.', '2026-10-22', 3, '2026-11-17', 7),

                                                                                                                  (30, 'Create Development Roadmap', FALSE, 'Break the project into milestones and establish development priorities.', '2026-11-23', 0, '2026-11-17', 8),
                                                                                                                  (31, 'Set Up Project Repository', TRUE, 'Initialize the Git repository and configure the project structure.', '2026-10-24', 1, '2026-11-17', 8),
                                                                                                                  (32, 'Choose Mobile Framework', TRUE, 'Evaluate Flutter, React Native, and native development options.', '2026-10-25', 2, '2026-11-17', 8),
                                                                                                                  (33, 'Prepare UI Design System', FALSE, 'Define typography, colors, spacing, and reusable interface components.', '2026-10-26', 3, '2026-11-17', 8),
                                                                                                                  (34, 'Design Onboarding Screens', TRUE, 'Create onboarding screens introducing the app and its main features.', '2026-11-27', 4, '2026-11-17', 8),

                                                                                                                  (35, 'Implement User Authentication', TRUE, 'Develop secure registration, login, and password recovery functionality.', '2026-10-28', 0, '2026-11-17', 9),
                                                                                                                  (36, 'Build Home Dashboard', TRUE, 'Implement the main dashboard displaying important information and shortcuts.', '2026-11-29', 1, '2026-11-17', 9),
                                                                                                                  (37, 'Integrate Push Notifications', TRUE, 'Configure push notifications for important user updates and events.', '2026-10-30', 2, '2026-11-17', 9),
                                                                                                                  (38, 'Implement User Profile', FALSE, 'Create profile management with editable personal information and settings.', '2026-12-01', 3, '2026-11-17', 9),
                                                                                                                  (39, 'Connect Backend API', TRUE, 'Integrate mobile application screens with backend services and endpoints.', '2026-12-02', 4, '2026-11-17', 9),

                                                                                                                  (40, 'Review Authentication Module', TRUE, 'Review authentication implementation for code quality, security, and consistency.', '2026-12-03', 0, '2026-11-17', 10),
                                                                                                                  (41, 'Review API Integration', FALSE, 'Check API integration, error handling, and data validation.', '2026-12-04', 1, '2026-11-17', 10),
                                                                                                                  (42, 'Refactor Navigation Logic', FALSE, 'Improve navigation architecture and simplify screen transitions.', '2026-12-05', 2, '2026-11-17', 10),
                                                                                                                  (43, 'Optimize State Management', FALSE, 'Review application state management and eliminate unnecessary updates.', '2026-12-06', 3, '2026-11-17', 10),

                                                                                                                  (44, 'Test Login and Registration', TRUE, 'Verify authentication workflows, validation, and error handling.', '2026-12-07', 0, '2026-11-17', 11),
                                                                                                                  (45, 'Test Different Screen Sizes', TRUE, 'Check layouts and interface responsiveness across different mobile devices.', '2026-12-08', 1, '2026-11-17', 11),
                                                                                                                  (46, 'Perform Performance Testing', FALSE, 'Measure application performance, memory usage, and loading times.', '2026-12-09', 2, '2026-11-17', 11),
                                                                                                                  (47, 'Fix Critical Bugs', FALSE, 'Resolve critical issues identified during functional and regression testing.', '2026-12-10', 3, '2026-11-17', 11),

                                                                                                                  (48, 'Prepare App Store Assets', FALSE, 'Create application icons, screenshots, descriptions, and promotional materials.', '2026-12-11', 0, '2026-11-17', 12),
                                                                                                                  (49, 'Publish Android Application', FALSE, 'Prepare the production build and submit the application to Google Play.', '2026-12-12', 1, '2026-11-17', 12),
                                                                                                                  (50, 'Publish iOS Application', FALSE, 'Prepare the iOS production build and submit the application to the App Store.', '2026-12-13', 2, '2026-11-17', 12);



INSERT INTO cards (id, title, completed, description, due_date, position_in_column, creation_date, column_id) VALUES
                                                                                                                  (51, 'Brainstorm Campaign Ideas', TRUE, 'Generate creative marketing campaign concepts to promote the brand and its products.', '2026-10-19', 0, '2026-11-17', 13),
                                                                                                                  (52, 'Identify Target Audience', FALSE, 'Define audience demographics, interests, behaviors, and customer segments.', '2026-11-20', 1, '2026-11-17', 13),
                                                                                                                  (53, 'Research Marketing Trends', TRUE, 'Explore current marketing trends and identify opportunities for the campaign.', '2026-10-21', 2, '2026-11-17', 13),

                                                                                                                  (54, 'Define Campaign Objectives', FALSE, 'Establish measurable marketing goals and key performance indicators.', '2026-10-22', 0, '2026-11-17', 14),
                                                                                                                  (55, 'Set Marketing Budget', TRUE, 'Allocate the campaign budget across advertising channels and promotional activities.', '2026-11-23', 1, '2026-11-17', 14),
                                                                                                                  (56, 'Choose Marketing Channels', TRUE, 'Select suitable platforms including social media, email, and paid advertising.', '2026-11-24', 2, '2026-11-17', 14),

                                                                                                                  (57, 'Write Social Media Posts', TRUE, 'Prepare engaging social media captions and promotional messages for the campaign.', '2026-11-25', 0, '2026-11-17', 15),
                                                                                                                  (58, 'Design Campaign Graphics', TRUE, 'Create visual assets, banners, and promotional graphics for different platforms.', '2026-11-26', 1, '2026-11-17', 15),
                                                                                                                  (59, 'Create Promotional Video', FALSE, 'Produce a short promotional video highlighting the campaign message and benefits.', '2026-11-27', 2, '2026-11-17', 15),

                                                                                                                  (60, 'Review Marketing Copy', FALSE, 'Check all campaign texts for grammar, clarity, tone, and brand consistency.', '2026-11-28', 0, '2026-11-17', 16),
                                                                                                                  (61, 'Review Visual Assets', TRUE, 'Verify that all graphics and promotional materials follow brand guidelines.', '2026-11-29', 1, '2026-11-17', 16),
                                                                                                                  (62, 'Get Stakeholder Approval', FALSE, 'Present campaign materials to stakeholders and collect final feedback.', '2026-11-30', 2, '2026-11-17', 16),

                                                                                                                  (63, 'Schedule Social Media Posts', TRUE, 'Prepare and schedule campaign posts across selected social media platforms.', '2026-12-01', 0, '2026-11-17', 17),
                                                                                                                  (64, 'Schedule Email Newsletter', FALSE, 'Configure the email campaign and schedule newsletters for subscribers.', '2026-12-02', 1, '2026-11-17', 17),
                                                                                                                  (65, 'Configure Advertising Campaigns', FALSE, 'Set up paid advertising campaigns, targeting, and publication dates.', '2026-12-03', 2, '2026-11-17', 17),

                                                                                                                  (66, 'Launch Marketing Campaign', FALSE, 'Publish approved campaign materials across all selected marketing channels.', '2026-12-04', 0, '2026-11-17', 18),
                                                                                                                  (67, 'Monitor Campaign Performance', FALSE, 'Track impressions, engagement, conversions, and other campaign performance metrics.', '2026-12-05', 1, '2026-11-17', 18),
                                                                                                                  (68, 'Prepare Campaign Results Report', FALSE, 'Summarize campaign performance and identify opportunities for future improvements.', '2026-12-06', 2, '2026-11-17', 18);


INSERT INTO cards (id, title, completed, description, due_date, position_in_column, creation_date, column_id) VALUES
                                                                                                                  (69, 'Conduct Market Research', TRUE, 'Analyze market trends, customer needs, and potential opportunities for the new product.', '2026-10-19', 0, '2026-11-17', 19),
                                                                                                                  (70, 'Analyze Competitor Products', TRUE, 'Research competing products and identify their strengths, weaknesses, and unique features.', '2026-11-10', 1, '2026-11-17', 19),
                                                                                                                  (71, 'Collect Customer Feedback', TRUE, 'Gather feedback from potential customers to understand product expectations.', '2026-11-21', 2, '2026-11-17', 19),

                                                                                                                  (72, 'Define Product Requirements', TRUE, 'Document functional and non-functional requirements for the product.', '2026-11-15', 0, '2026-11-17', 20),
                                                                                                                  (73, 'Create Product Roadmap', TRUE, 'Establish development milestones, priorities, and project deadlines.', '2026-11-23', 1, '2026-11-17', 20),
                                                                                                                  (74, 'Estimate Development Costs', FALSE, 'Calculate expected development expenses, resources, and operational costs.', '2026-11-17', 2, '2026-11-17', 20),

                                                                                                                  (75, 'Build Product Prototype', TRUE, 'Develop an initial prototype to demonstrate the main product functionality.', '2026-11-25', 0, '2026-11-17', 21),
                                                                                                                  (76, 'Implement Core Features', FALSE, 'Develop the essential product features according to the approved requirements.', '2026-11-17', 1, '2026-11-17', 21),
                                                                                                                  (77, 'Develop User Interface', FALSE, 'Create the product interface and implement the approved visual design.', '2026-11-29', 2, '2026-11-17', 21),
                                                                                                                  (78, 'Integrate External Services', FALSE, 'Connect the product with required third-party APIs and external services.', '2026-12-01', 3, '2026-11-17', 21),

                                                                                                                  (79, 'Perform Functional Testing', TRUE, 'Verify that all product features work according to the specifications.', '2026-12-02', 0, '2026-11-17', 22),
                                                                                                                  (80, 'Conduct Usability Testing', FALSE, 'Evaluate the product experience with users and identify usability problems.', '2026-12-03', 1, '2026-11-17', 22),
                                                                                                                  (81, 'Fix Critical Bugs', FALSE, 'Resolve critical issues discovered during testing and verification.', '2026-12-04', 2, '2026-11-17', 22),

                                                                                                                  (82, 'Prepare Product Documentation', TRUE, 'Create user manuals, technical documentation, and product support materials.', '2026-12-05', 0, '2026-11-17', 23),
                                                                                                                  (83, 'Prepare Launch Campaign', FALSE, 'Develop promotional materials and coordinate marketing activities for launch.', '2026-12-07', 1, '2026-11-17', 23),
                                                                                                                  (84, 'Finalize Production Environment', FALSE, 'Configure production infrastructure and verify deployment readiness.', '2026-12-09', 2, '2026-11-17', 23),

                                                                                                                  (85, 'Release Product Version 1.0', TRUE, 'Publish the first stable product version for customers and end users.', '2026-12-11', 0, '2026-11-17', 24),
                                                                                                                  (86, 'Monitor Product Performance', FALSE, 'Monitor product stability, customer feedback, and performance after release.', '2026-12-13', 1, '2026-11-17', 24);


INSERT INTO cards (id, title, completed, description, due_date, position_in_column, creation_date, column_id) VALUES
                                                                                                                  (87, 'Brainstorm Blog Topics', TRUE, 'Generate engaging blog topic ideas aligned with the content strategy and target audience.', '2026-11-15', 0, '2026-11-17', 25),
                                                                                                                  (88, 'Research Trending Topics', TRUE, 'Identify trending subjects and keywords relevant to the brand and its audience.', '2026-11-20', 1, '2026-11-17', 25),

                                                                                                                  (89, 'Write Blog Article', TRUE, 'Create an informative blog article optimized for readability and search engines.', '2026-11-21', 0, '2026-11-17', 26),
                                                                                                                  (90, 'Prepare Social Media Captions', FALSE, 'Write engaging captions for upcoming social media posts.', '2026-11-13', 1, '2026-11-17', 26),
                                                                                                                  (91, 'Create Newsletter Content', FALSE, 'Prepare newsletter content featuring company updates and relevant announcements.', '2026-11-15', 2, '2026-11-17', 26),

                                                                                                                  (92, 'Edit Blog Article', TRUE, 'Improve article structure, readability, grammar, and overall writing quality.', '2026-11-24', 0, '2026-11-17', 27),
                                                                                                                  (93, 'Optimize SEO Keywords', FALSE, 'Review keyword placement, headings, metadata, and search engine optimization.', '2026-11-25', 1, '2026-11-17', 27),

                                                                                                                  (94, 'Review Social Media Content', TRUE, 'Verify social media copy for tone, consistency, accuracy, and brand alignment.', '2026-11-26', 0, '2026-11-17', 28),
                                                                                                                  (95, 'Review Newsletter Draft', FALSE, 'Check newsletter content for clarity, formatting, and relevant links.', '2026-11-27', 1, '2026-11-17', 28),

                                                                                                                  (96, 'Approve Blog Article', TRUE, 'Obtain final approval for the blog article before publication.', '2026-11-28', 0, '2026-11-17', 29),
                                                                                                                  (97, 'Approve Social Media Graphics', TRUE, 'Verify and approve visual materials prepared for social media distribution.', '2026-11-29', 1, '2026-11-17', 29),

                                                                                                                  (98, 'Publish Blog Article', TRUE, 'Publish the approved blog article on the company website.', '2026-11-30', 0, '2026-11-17', 30),
                                                                                                                  (99, 'Schedule Social Media Posts', FALSE, 'Publish and schedule approved social media content across selected platforms.', '2026-12-01', 1, '2026-11-17', 30);



INSERT INTO cards (id, title, completed, description, due_date, position_in_column, creation_date, column_id) VALUES
(100, 'Update Website Dependencies', FALSE, 'Update frontend and backend dependencies to their latest stable versions.', '2026-11-20', 0, '2026-11-17', 31),
(101, 'Monitor Website Performance', FALSE, 'Monitor website loading speed, server response times, and overall performance.', '2026-11-22', 1, '2026-11-17', 31),
(102, 'Perform Database Backup', TRUE, 'Create and verify a complete backup of the production database.', '2026-11-19', 2, '2026-11-17', 31),
(103, 'Renew SSL Certificate', FALSE, 'Check SSL certificate expiration and renew it to maintain secure connections.', '2026-11-25', 3, '2026-11-17', 31),

(104, 'Redesign Customer Dashboard', FALSE, 'Redesign the customer dashboard pending approval of the new UI concept.', '2026-12-01', 0, '2026-11-17', 32),
(105, 'Integrate Payment Provider', FALSE, 'Implement an additional payment gateway after receiving business approval.', '2026-12-05', 1, '2026-11-17', 32),
(106, 'Add Multi-language Support', FALSE, 'Prepare website localization and translation support for additional languages.', '2026-12-10', 2, '2026-11-17', 32);



INSERT INTO labels (id, name, color, board_name) VALUES

(1, 'Frontend', '#3B82F6', 1),
(2, 'Backend', '#8B5CF6', 1),
(3, 'UI/UX', '#EC4899', 1),
(4, 'Bug Fix', '#EF4444', 1),
(5, 'Documentation', '#14B8A6', 1),


(6, 'Android', '#22C55E', 2),
(7, 'iOS', '#64748B', 2),
(8, 'Authentication', '#8B5CF6', 2),
(9, 'UI Design', '#EC4899', 2),
(10, 'Performance', '#F59E0B', 2),
(11, 'API Integration', '#3B82F6', 2),


(12, 'Social Media', '#3B82F6', 3),
(13, 'Email Marketing', '#8B5CF6', 3),
(14, 'Advertising', '#EF4444', 3),
(15, 'SEO', '#22C55E', 3),
(16, 'Analytics', '#F59E0B', 3),


(17, 'Research', '#06B6D4', 4),
(18, 'Features', '#3B82F6', 4),
(19, 'Prototype', '#8B5CF6', 4),
(20, 'Quality Assurance', '#22C55E', 4),
(21, 'Bug Fix', '#EF4444', 4),
(22, 'Release', '#F59E0B', 4),


(23, 'Blog Post', '#3B82F6', 5),
(24, 'Social Media', '#EC4899', 5),
(25, 'Newsletter', '#8B5CF6', 5),
(26, 'SEO Content', '#22C55E', 5),
(27, 'Copywriting', '#F59E0B', 5),


(28, 'Visual Content', '#06B6D4', 5),
(29, 'Editorial', '#64748B', 5);



INSERT INTO card_labels (card_id, label_id) VALUES
(1, 1),
(1, 3),
(3, 2),
(6, 3),
(7, 1),
(7, 3),
(10, 2),
(12, 2),
(15, 1),
(17, 4),
(20, 4),
(22, 1),
(24, 5),

(26, 9),
(27, 6),
(29, 10),
(30, 8),
(30, 11),
(32, 6),
(35, 8),
(36, 9),
(39, 11),
(40, 8),
(44, 10),
(47, 10),
(48, 6),

(51, 12),
(52, 12),
(54, 15),
(55, 14),
(57, 12),
(57, 13),
(58, 12),
(60, 15),
(63, 13),
(66, 14),
(67, 16),

(69, 17),
(70, 17),
(72, 18),
(75, 19),
(75, 18),
(77, 18),
(79, 20),
(80, 20),
(81, 21),
(82, 20),
(85, 22),

(87, 23),
(88, 26),
(89, 23),
(89, 26),
(90, 24),
(92, 27),
(94, 24),
(96, 27),
(98, 23),
(99, 24);


INSERT INTO card_assignees (card_id, user_id) VALUES
                                                  (1, 1),
                                                  (1, 3),
                                                  (2, 6),
                                                  (2, 7),
                                                  (3, 8),
                                                  (3, 9),
                                                  (4, 1),
                                                  (5, 3),
                                                  (5, 6),
                                                  (6, 7),
                                                  (7, 8),
                                                  (7, 9),
                                                  (8, 1),
                                                  (9, 3),
                                                  (10, 6),
                                                  (10, 9),
                                                  (12, 7),
                                                  (15, 8),
                                                  (20, 1),
                                                  (25, 3);


INSERT INTO card_assignees (card_id, user_id) VALUES
                                                  (26, 1),
                                                  (26, 3),
                                                  (27, 7),
                                                  (28, 9),
                                                  (29, 1),
                                                  (29, 7),
                                                  (30, 3),
                                                  (31, 9),
                                                  (32, 1),
                                                  (33, 3),
                                                  (33, 9),
                                                  (34, 7),
                                                  (35, 1),
                                                  (36, 3),
                                                  (38, 7);

INSERT INTO card_assignees (card_id, user_id) VALUES
                                                  (51, 1),
                                                  (51, 2),
                                                  (52, 8),
                                                  (53, 1),
                                                  (54, 2),
                                                  (55, 8),
                                                  (55, 1),
                                                  (57, 2),
                                                  (59, 8),
                                                  (60, 1),
                                                  (63, 2),
                                                  (66, 8),
                                                  (68, 1);

INSERT INTO card_assignees (card_id, user_id) VALUES
                                                  (69, 1),
                                                  (69, 3),
                                                  (70, 9),
                                                  (72, 1),
                                                  (74, 3),
                                                  (76, 9),
                                                  (78, 1),
                                                  (80, 3),
                                                  (83, 9),
                                                  (86, 1);


INSERT INTO card_assignees (card_id, user_id) VALUES
                                                  (87, 4),
                                                  (87, 9),
                                                  (87, 10),
                                                  (88, 4),
                                                  (88, 9),
                                                  (89, 10),
                                                  (89, 4),
                                                  (90, 9),
                                                  (90, 10),
                                                  (91, 4),
                                                  (92, 9),
                                                  (92, 10),
                                                  (93, 4),
                                                  (94, 9),
                                                  (94, 10),
                                                  (95, 4),
                                                  (96, 9),
                                                  (97, 10),
                                                  (98, 4),
                                                  (99, 9);



INSERT INTO comments (id, content, created_at, card_id, user_id) VALUES
(1, 'I have reviewed the initial requirements. Everything looks clear, but we should clarify the authentication requirements.', '2026-11-17 09:15:00', 1, 6),
(2, 'The competitor analysis is complete. I found a few useful ideas for improving our navigation.', '2026-11-17 10:20:00', 3, 6),
(3, 'Can we consider adding a dark mode to the website in the future?', '2026-11-17 11:05:00', 3, 6),
(4, 'The homepage wireframe is ready for review. Please share your feedback.', '2026-11-17 12:30:00', 6, 6),
(5, 'I suggest using reusable components for the navigation and footer.', '2026-11-17 13:45:00', 8, 6),
(6, 'The contact form should include server-side validation as well.', '2026-11-18 09:10:00', 17, 6),
(7, 'I noticed a layout issue on smaller mobile screens. We should fix it before deployment.', '2026-11-18 10:25:00', 18, 6),
(8, 'The API integration is progressing well. Most endpoints are responding correctly.', '2026-11-18 11:40:00', 19, 6),
(9, 'Please update the documentation after implementing the new authentication flow.', '2026-11-18 14:00:00', 14, 6),
(10, 'The database backup was completed successfully. I also verified that the backup file is accessible.', '2026-11-18 15:20:00', 102, 6),
(11, 'The SSL certificate renewal is scheduled. I will verify the configuration afterward.', '2026-11-19 08:45:00', 103, 6),
(12, 'Website performance has improved after optimizing image sizes.', '2026-11-19 10:15:00', 101, 6),

(13, 'The onboarding design looks clean and intuitive. I especially like the simplified navigation.', '2026-11-17 09:30:00', 34, 7),
(14, 'We should test authentication on both Android and iOS devices.', '2026-11-17 11:15:00', 35, 7),
(15, 'Push notifications are working in the development environment.', '2026-11-17 14:10:00', 37, 7),
(16, 'The profile screen needs a loading indicator while user data is being fetched.', '2026-11-18 09:40:00', 38, 7),
(17, 'I have completed the API integration review. A few error handling improvements are needed.', '2026-11-18 11:50:00', 41, 7),
(18, 'Navigation transitions are smoother after the latest refactoring.', '2026-11-18 13:25:00', 42, 7),
(19, 'The application performs well on newer devices, but older devices need additional testing.', '2026-11-19 10:00:00', 45, 7),
(20, 'We should add tests for password recovery before the next release.', '2026-11-19 12:15:00', 44, 7),

(21, 'The campaign concept is ready. I recommend focusing on the benefits rather than product features.', '2026-11-17 10:00:00', 51, 8),
(22, 'The target audience research suggests that Instagram and LinkedIn should be our primary channels.', '2026-11-17 12:20:00', 52, 8),
(23, 'The marketing budget has been reviewed and approved.', '2026-11-18 09:00:00', 55, 8),
(24, 'The first batch of social media captions is ready. Please check the tone of voice.', '2026-11-18 10:45:00', 57, 8),
(25, 'The promotional graphics need slightly stronger contrast to improve readability.', '2026-11-18 13:15:00', 58, 8),
(26, 'I suggest adding a clear call to action to the newsletter.', '2026-11-19 09:25:00', 60, 8),
(27, 'The campaign visuals have been approved by the marketing manager.', '2026-11-19 11:30:00', 62, 8),
(28, 'All scheduled posts have been checked. The publication dates look correct.', '2026-11-19 14:20:00', 63, 8),
(29, 'We should monitor conversion rates in addition to impressions and engagement.', '2026-11-20 09:10:00', 67, 8),

(30, 'The market research indicates strong interest in the proposed product features.', '2026-11-17 09:50:00', 69, 9),
(31, 'The competitor comparison is complete. We have identified three opportunities for differentiation.', '2026-11-17 13:00:00', 70, 9),
(32, 'The product requirements document has been reviewed and approved.', '2026-11-18 09:15:00', 72, 9),
(33, 'The prototype is ready for an internal demonstration.', '2026-11-18 11:35:00', 75, 9),
(34, 'The current prototype needs some improvements to the user navigation.', '2026-11-18 14:30:00', 75, 9),
(35, 'Functional testing passed for all major features.', '2026-11-19 10:40:00', 79, 9),
(36, 'I found a minor issue with the settings screen. It does not save changes consistently.', '2026-11-19 12:05:00', 80, 9),
(37, 'The user documentation is ready. We should review the screenshots before publishing.', '2026-11-19 15:10:00', 82, 9),
(38, 'Version 1.0 has been released successfully. Great work, everyone!', '2026-11-20 09:30:00', 85, 9),

(39, 'I have added several new blog topics to the content calendar.', '2026-11-17 10:10:00', 87, 10),
(40, 'The blog article draft is ready. Please review the introduction and conclusion.', '2026-11-17 13:20:00', 89, 10),
(41, 'The SEO keywords have been incorporated naturally into the article.', '2026-11-18 09:35:00', 92, 10),
(42, 'The newsletter needs a more engaging subject line to improve open rates.', '2026-11-18 11:45:00', 90, 10),
(43, 'The social media captions have been reviewed and are consistent with our brand voice.', '2026-11-19 10:20:00', 94, 10),
(44, 'The blog article has been approved for publication.', '2026-11-19 13:40:00', 96, 10),
(45, 'The article is now live on the website. I have checked all links and images.', '2026-11-20 09:15:00', 98, 10),

(46, 'Could we postpone the dashboard redesign until the current release is stable?', '2026-11-19 11:00:00', 104, 6),
(47, 'The payment provider integration is waiting for confirmation from the business team.', '2026-11-19 14:45:00', 105, 6),
(48, 'I tested the website on Chrome, Firefox, and Safari. Everything looks good so far.', '2026-11-20 10:30:00', 21, 6);