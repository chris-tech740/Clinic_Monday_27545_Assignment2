import React from 'react';

export default function StatsCards({ totalOffices, availableOffices, occupiedOffices }) {
  return (
    <div className="stats-grid">
      <div className="stat-card">
        <div className="stat-content">
          <span className="stat-label">Total Offices</span>
          <span className="stat-value">{totalOffices}</span>
          <span className="stat-desc">All registered rooms</span>
        </div>
      </div>

      <div className="stat-card">
        <div className="stat-content">
          <span className="stat-label">Available Offices</span>
          <span className="stat-value">{availableOffices}</span>
          <span className="stat-desc">Ready for assignment</span>
        </div>
      </div>

      <div className="stat-card">
        <div className="stat-content">
          <span className="stat-label">Doctor Assigned</span>
          <span className="stat-value">{occupiedOffices}</span>
          <span className="stat-desc">Active occupied rooms</span>
        </div>
      </div>
    </div>
  );
}
