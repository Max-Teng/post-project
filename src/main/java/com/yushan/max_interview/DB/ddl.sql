CREATE TABLE user (
      id INT PRIMARY KEY AUTOINCREMENT,
      user_name VARCHAR(50) NOT NULL,
      email VARCHAR(100),
      password VARCHAR(255) NOT NULL,
      cover_image VARCHAR(255),
      biography TEXT,
      created_at TIMESTAMP
);

CREATE TABLE post (
      id INTEGER PRIMARY KEY AUTOINCREMENT,
      user_id INTEGER NOT NULL,
      content TEXT NOT NULL,
      image VARCHAR(255),
      created_at TIMESTAMP,
      CONSTRAINT fk_post_user FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE
);

CREATE TABLE comment (
     id INTEGER PRIMARY KEY AUTOINCREMENT,
     user_id INTEGER NOT NULL,
     post_id INTEGER NOT NULL,
     content TEXT NOT NULL,
     created_at TIMESTAMP,
     CONSTRAINT fk_comment_user FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE,
     CONSTRAINT fk_comment_post FOREIGN KEY (post_id) REFERENCES post(id) ON DELETE CASCADE
);
