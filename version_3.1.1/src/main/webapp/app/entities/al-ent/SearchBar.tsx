import React, { useState } from "react";

function SearchBar({ list, onSearch }) {
  const [inputValue, setInputValue] = useState("");
  const [selectedItem, setSelectedItem] = useState(null);

  const handleInputChange = (e) => {
    const value = e.target.value;
    setInputValue(value);
    onSearch(value);
  };

  const handleItemClick = (item) => {
    setInputValue(item);
    setSelectedItem(item);
    onSearch(item);
  };

  return (
    <div>
      <input
        type="text"
        placeholder="Search"
        value={selectedItem || inputValue}
        onChange={handleInputChange}
      />
      <ul>
        {list.map((item, index) => (
          <li key={index} onClick={() => handleItemClick(item)}>
            {item}
          </li>
        ))}
      </ul>
    </div>
  );
}

export default SearchBar;