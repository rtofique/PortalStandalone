import React, { Component } from 'react';

//for now this will simply return the json output in a box


//the json has already been passed in props to this component

export default class NumberLookupOutput extends React.Component
{
	render()
	{
		return (
				<h1>{this.props.output}</h1>
		);
	}
}
