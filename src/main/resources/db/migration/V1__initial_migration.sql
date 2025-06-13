-- USERS TABLE
CREATE TABLE users (
                       id SERIAL PRIMARY KEY,
                       username VARCHAR(50) UNIQUE NOT NULL,
                       email VARCHAR(100) UNIQUE NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       name VARCHAR(100),
                       bio TEXT,
                       country VARCHAR(100),
                       gender VARCHAR(20),
                       profile_picture TEXT,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_users_username ON users(username);
CREATE INDEX idx_users_created_at ON users(created_at);

-- POSTS TABLE
CREATE TABLE posts (
                       id SERIAL PRIMARY KEY,
                       user_id INTEGER NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                       caption TEXT,
                       image_url TEXT,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE INDEX idx_posts_user_id ON posts(user_id);
CREATE INDEX idx_posts_created_at ON posts(created_at);

-- COMMENTS TABLE
CREATE TABLE comments (
                          id SERIAL PRIMARY KEY,
                          post_id INTEGER NOT NULL REFERENCES posts(id) ON DELETE CASCADE,
                          user_id INTEGER NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                          text TEXT NOT NULL,
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE INDEX idx_comments_post_id ON comments(post_id);
CREATE INDEX idx_comments_user_id ON comments(user_id);
CREATE INDEX idx_comments_created_at ON comments(created_at);

-- COMMENT LIKES TABLE
CREATE TABLE comment_likes (
                               id SERIAL PRIMARY KEY,
                               comment_id INTEGER NOT NULL REFERENCES comments(id) ON DELETE CASCADE,
                               user_id INTEGER NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                               created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                               UNIQUE(comment_id, user_id)
);
CREATE INDEX idx_comment_likes_comment_id ON comment_likes(comment_id);
CREATE INDEX idx_comment_likes_user_id ON comment_likes(user_id);

-- POST LIKES TABLE
CREATE TABLE likes (
                       id SERIAL PRIMARY KEY,
                       post_id INTEGER NOT NULL REFERENCES posts(id) ON DELETE CASCADE,
                       user_id INTEGER NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       UNIQUE(post_id, user_id)
);
CREATE INDEX idx_likes_post_id ON likes(post_id);
CREATE INDEX idx_likes_user_id ON likes(user_id);

-- SAVED POSTS TABLE
CREATE TABLE saved_posts (
                             id SERIAL PRIMARY KEY,
                             user_id INTEGER NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                             post_id INTEGER NOT NULL REFERENCES posts(id) ON DELETE CASCADE,
                             saved_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                             UNIQUE(user_id, post_id)
);
CREATE INDEX idx_saved_posts_user_id ON saved_posts(user_id);
CREATE INDEX idx_saved_posts_post_id ON saved_posts(post_id);

-- FOLLOW RELATIONSHIPS
CREATE TABLE follows (
                         id SERIAL PRIMARY KEY,
                         follower_id INTEGER NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                         following_id INTEGER NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         UNIQUE(follower_id, following_id)
);
CREATE INDEX idx_follows_follower_id ON follows(follower_id);
CREATE INDEX idx_follows_following_id ON follows(following_id);

-- STORIES TABLE
CREATE TABLE stories (
                         id SERIAL PRIMARY KEY,
                         user_id INTEGER NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                         image_url TEXT NOT NULL,
                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         expires_at TIMESTAMP NOT NULL
);
CREATE INDEX idx_stories_user_id ON stories(user_id);
CREATE INDEX idx_stories_expires_at ON stories(expires_at);

-- NOTIFICATIONS TABLE
CREATE TABLE notifications (
                               id SERIAL PRIMARY KEY,
                               user_id INTEGER NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                               message TEXT NOT NULL,
                               is_read BOOLEAN DEFAULT FALSE,
                               created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE INDEX idx_notifications_user_id ON notifications(user_id);
CREATE INDEX idx_notifications_is_read ON notifications(is_read);
CREATE INDEX idx_notifications_created_at ON notifications(created_at);

-- Bookmarks
CREATE TABLE bookmarks (
                           id SERIAL PRIMARY KEY,
                           user_id INTEGER NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                           post_id INTEGER NOT NULL REFERENCES posts(id) ON DELETE CASCADE,
                           bookmarked_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                           UNIQUE(user_id, post_id)
);
CREATE INDEX idx_bookmarks_user_id ON bookmarks(user_id);
CREATE INDEX idx_bookmarks_post_id ON bookmarks(post_id);
CREATE INDEX idx_bookmarks_bookmarked_at ON bookmarks(bookmarked_at);

-- Events
CREATE TABLE events (
                        id SERIAL PRIMARY KEY,
                        user_id INTEGER NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                        name VARCHAR(255) NOT NULL,
                        description TEXT,
                        location VARCHAR(255),
                        coordinates_lat DOUBLE PRECISION,
                        coordinates_lng DOUBLE PRECISION,
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Event images
CREATE TABLE event_images (
                              id SERIAL PRIMARY KEY,
                              event_id INTEGER NOT NULL REFERENCES events(id) ON DELETE CASCADE,
                              image_url TEXT NOT NULL
);

-- Event Likes
CREATE TABLE event_likes (
                             id SERIAL PRIMARY KEY,
                             event_id INTEGER NOT NULL REFERENCES events(id) ON DELETE CASCADE,
                             user_id INTEGER NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                             created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                             UNIQUE(event_id, user_id)
);


-- Event Bookmarks
CREATE TABLE event_saves (
                             id SERIAL PRIMARY KEY,
                             event_id INTEGER NOT NULL REFERENCES events(id) ON DELETE CASCADE,
                             user_id INTEGER NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                             saved_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                             UNIQUE(event_id, user_id)
);

-- Event comments
CREATE TABLE event_comments (
                                id SERIAL PRIMARY KEY,
                                event_id INTEGER NOT NULL REFERENCES events(id) ON DELETE CASCADE,
                                user_id INTEGER NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                                text TEXT NOT NULL,
                                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

