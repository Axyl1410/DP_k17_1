-- Tạo table users cho chức năng login/register
CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    full_name VARCHAR(100) NOT NULL,
    role VARCHAR(20) DEFAULT 'USER', -- ADMIN, USER
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    is_active BOOLEAN DEFAULT TRUE
);

-- Thêm index cho performance
CREATE INDEX idx_username ON users(username);
CREATE INDEX idx_email ON users(email);

-- Insert một số user mẫu (password: 123456)
INSERT INTO users (username, email, password_hash, full_name, role) VALUES
('admin', 'admin@example.com', 'jZae727K08KaOmKSgOaGzww/XVqGr/PKEgIM1rcZww8=', 'Administrator', 'ADMIN'),
('user1', 'user1@example.com', 'jZae727K08KaOmKSgOaGzww/XVqGr/PKEgIM1rcZww8=', 'Nguyễn Văn A', 'USER'),
('user2', 'user2@example.com', 'jZae727K08KaOmKSgOaGzww/XVqGr/PKEgIM1rcZww8=', 'Trần Thị B', 'USER');
