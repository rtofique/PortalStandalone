import React, { Component } from 'react';

//for now this will simply return the json output in a box


//the json has already been passed in props to this component

export default class NumberLookupOutput extends React.Component
{


	getJSONObj()
	{
		if(this.props.output === "") return "abc";
		let obj = JSON.stringify(this.props.output);
		console.log(obj);
		return obj;
	}

	render()
	{
		const obj = this.getJSONObj();
		return (
				<div>
					<h3>{obj}</h3>
				</div>
		);
	}
}


