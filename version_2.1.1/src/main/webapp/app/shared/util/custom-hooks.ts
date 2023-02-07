import React, {useState, useEffect} from 'react';

export const useLocalStorage = (storageKey, defaultState) => {
  const [value, setValue] = useState(
    localStorage.getItem(storageKey) ?? defaultState
  );

  useEffect(() => {
    localStorage.setItem(storageKey, JSON.stringify(value));
  }, [value])

  return [value, setValue];
}
