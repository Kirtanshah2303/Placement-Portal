import React, { Component } from 'react';
import { BrowserRouter, Link } from 'react-router-dom';
import './landing.scss';

const Landing = () => {
  return (
    <>
      <div className="container1">
        <img src="![](../../../content/images/charusat.jpg)" alt="Charusat Image" width="100%" />

        <div className="text-block">
          <h1>A one stop portal for Placements</h1>
          <br />
          <p> Welcome to the placements portal.</p>
        </div>

        <div className="buttons1">
          <button className="btns1" style={{ textDecoration: 'none', color: 'white' }}>
            Student
          </button>

          <br />
          <br />
          <br />
          <br />
          <button className="btns1" style={{ textDecoration: 'none', color: 'white' }}>
            Recruiter
          </button>
        </div>
      </div>
    </>
  );
};
export default Landing;
