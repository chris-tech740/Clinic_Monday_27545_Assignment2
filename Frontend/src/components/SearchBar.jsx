import React from 'react';
import { Search, X } from 'lucide-react';

export default function SearchBar({ 
  searchTerm, 
  setSearchTerm, 
  activeFilter, 
  setActiveFilter, 
  totalResults 
}) {
  return (
    <div className="search-section">
      <div className="search-bar-row">
        {/* Search input with empty placeholder */}
        <div className="search-input-wrapper">
          <Search className="search-icon" size={17} />
          <input
            type="text"
            className="search-input"
            placeholder=""
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
          />
          {searchTerm && (
            <button 
              type="button"
              className="clear-search-btn"
              onClick={() => setSearchTerm('')}
              title="Clear search"
            >
              <X size={15} />
            </button>
          )}
        </div>

        {/* Filter pills */}
        <div className="filter-group">
          <button
            type="button"
            className={`filter-btn ${activeFilter === 'all' ? 'active' : ''}`}
            onClick={() => setActiveFilter('all')}
          >
            All
          </button>
          <button
            type="button"
            className={`filter-btn ${activeFilter === 'available' ? 'active' : ''}`}
            onClick={() => setActiveFilter('available')}
          >
            Available
          </button>
          <button
            type="button"
            className={`filter-btn ${activeFilter === 'assigned' ? 'active' : ''}`}
            onClick={() => setActiveFilter('assigned')}
          >
            Occupied
          </button>
        </div>
      </div>

      {searchTerm.trim() && (
        <div className="search-feedback">
          Showing <strong>{totalResults}</strong> result{totalResults !== 1 ? 's' : ''} matching "{searchTerm}"
        </div>
      )}
    </div>
  );
}
