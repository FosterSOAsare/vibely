-- EVENT COMMENT LIKES TABLE
CREATE TABLE event_comment_likes (
    id SERIAL PRIMARY KEY,
    event_comment_id INTEGER NOT NULL REFERENCES event_comments(id) ON DELETE CASCADE,
    user_id INTEGER NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(event_comment_id, user_id)
);

-- Indexes for performance
CREATE INDEX idx_event_comment_likes_event_comment_id ON event_comment_likes(event_comment_id);
CREATE INDEX idx_event_comment_likes_user_id ON event_comment_likes(user_id);