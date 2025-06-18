CREATE TABLE story_views (
    id SERIAL PRIMARY KEY,
    story_id INT NOT NULL,
    viewer_id INT NOT NULL,
    viewed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_story FOREIGN KEY (story_id) REFERENCES stories(id) ON DELETE CASCADE,
    CONSTRAINT fk_viewer FOREIGN KEY (viewer_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT unique_story_view UNIQUE (story_id, viewer_id)
);
