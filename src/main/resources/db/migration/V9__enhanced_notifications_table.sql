-- Add new columns to notifications table for enhanced notification functionality
ALTER TABLE notifications
    ADD COLUMN type VARCHAR(50),
    ADD COLUMN related_entity_id INTEGER,
    ADD COLUMN related_entity_type VARCHAR(20),
    ADD COLUMN triggered_by_user_id INTEGER;

-- Add foreign key constraint for triggered_by_user_id
ALTER TABLE notifications
    ADD CONSTRAINT fk_notifications_triggered_by_user
        FOREIGN KEY (triggered_by_user_id) REFERENCES users(id) ON DELETE CASCADE;

-- Add indexes for better query performance
CREATE INDEX idx_notifications_type ON notifications(type);
CREATE INDEX idx_notifications_user_unread ON notifications(user_id, is_read);
CREATE INDEX idx_notifications_related_entity ON notifications(related_entity_type, related_entity_id);
CREATE INDEX idx_notifications_triggered_by_user ON notifications(triggered_by_user_id);