import logo from './logo.svg';
import './App.css';
import ClassComponent from "./component/ClassComponent";

function App() {
  return (
    <div className="App">
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
          <ClassComponent />
      </header>
    </div>
  );
}

export default App;
