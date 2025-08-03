CREATE DATABASE IF NOT EXISTS roomsrooms;
USE rooms;

CREATE TABLE rooms (
    room_id VARCHAR(50) PRIMARY KEY,      -- Unique identifier for the room (Mã phòng)
    room_type VARCHAR(20) NOT NULL,       -- Type of room (e.g., 'LECTURE_HALL', 'COMPUTER_LAB', 'LABORATORY') (Loại phòng)
    building_block VARCHAR(50) NOT NULL,  -- Building or block where the room is located (Dãy nhà)
    area_sqm DECIMAL(8, 2) NOT NULL,      -- Area of the room in square meters (Diện tích)
    num_light_bulbs INT NOT NULL,         -- Number of light bulbs in the room (Số bóng đèn)
    start_operation_date DATE NOT NULL,   -- Date when the room started being used (Ngày bắt đầu hoạt động)

    -- Specific attributes for 'LECTURE_HALL'
    has_projector BOOLEAN,                -- TRUE if it has a projector, NULL if not a lecture hall (Có máy chiếu)

    -- Specific attributes for 'COMPUTER_LAB'
    num_computers INT,                    -- Number of computers, NULL if not a computer lab (Số máy tính)

    -- Specific attributes for 'LABORATORY'
    specialization VARCHAR(100),          -- Specialized field for the lab, NULL if not a laboratory (Chuyên ngành)
    capacity INT,                         -- Maximum capacity of the laboratory, NULL if not a laboratory (Sức chứa)
    has_sink BOOLEAN                      -- TRUE if it has a sink, NULL if not a laboratory (Có bồn rửa)
);

INSERT INTO rooms (
    room_id, room_type, building_block, area_sqm, num_light_bulbs, start_operation_date,
    has_projector, num_computers, specialization, capacity, has_sink
) VALUES
-- 1. Phòng Lý thuyết (LECTURE_HALL)
('LH101', 'LECTURE_HALL', 'Building A', 80.00, 8, '2018-09-01', TRUE, NULL, NULL, NULL, NULL),
('LH102', 'LECTURE_HALL', 'Building A', 60.00, 5, '2019-03-15', FALSE, NULL, NULL, NULL, NULL),
('LH201', 'LECTURE_HALL', 'Building B', 120.00, 15, '2017-01-20', TRUE, NULL, NULL, NULL, NULL),
('LH305', 'LECTURE_HALL', 'Building C', 75.00, 6, '2020-07-01', TRUE, NULL, NULL, NULL, NULL),
('LH306', 'LECTURE_HALL', 'Building C', 90.00, 10, '2021-02-10', TRUE, NULL, NULL, NULL, NULL),

-- 2. Phòng Máy tính (COMPUTER_LAB)
('CL101', 'COMPUTER_LAB', 'Building A', 75.00, 8, '2016-08-01', NULL, 50, NULL, NULL, NULL),
('CL102', 'COMPUTER_LAB', 'Building A', 60.00, 6, '2017-11-05', NULL, 20, NULL, NULL, NULL),
('CL201', 'COMPUTER_LAB', 'Building B', 90.00, 10, '2018-05-20', NULL, 60, NULL, NULL, NULL),
('CL301', 'COMPUTER_LAB', 'Building C', 50.00, 4, '2022-01-01', NULL, 30, NULL, NULL, NULL),
('CL302', 'COMPUTER_LAB', 'Building C', 60.00, 6, '2022-03-15', NULL, 40, NULL, NULL, NULL),

-- 3. Phòng Thí nghiệm (LABORATORY)
('LB101', 'LABORATORY', 'Building A', 70.00, 7, '2015-06-10', NULL, NULL, 'Chemistry', 25, TRUE),
('LB102', 'LABORATORY', 'Building A', 55.00, 5, '2016-02-28', NULL, NULL, 'Physics', 20, FALSE),
('LB201', 'LABORATORY', 'Building B', 85.00, 9, '2017-09-01', NULL, NULL, 'Biology', 30, TRUE),
('LB301', 'LABORATORY', 'Building C', 65.00, 6, '2021-04-01', NULL, NULL, 'Computer Science', 22, TRUE),
('LB302', 'LABORATORY', 'Building C', 70.00, 7, '2021-06-20', NULL, NULL, 'Environmental Science', 25, TRUE);