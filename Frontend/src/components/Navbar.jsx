import React from 'react';
import { Building2, Plus } from 'lucide-react';

export default function Navbar({ onOpenAddModal }) {
  return (
    <header className="navbar">
      <div className="navbar-container">
        <div className="brand">
          <div className="brand-icon">
            <Building2 size={22} />
          </div>
          <span className="brand-title">Clinic Care Management System</span>
        </div>

        <div className="navbar-actions">
          <button 
            type="button"
            className="btn btn-primary"
            onClick={onOpenAddModal}
          >
            <Plus size={16} strokeWidth={2.5} />
            <span>New Office</span>
          </button>
        </div>
      </div>
    </header>
  );
}
