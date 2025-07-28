
-- Add eventTime column (TIMESTAMP to store date and time)
ALTER TABLE events ADD COLUMN event_time TIMESTAMP;

-- Add price column (DECIMAL to store price with precision)
ALTER TABLE events ADD COLUMN price DECIMAL(10,2);

