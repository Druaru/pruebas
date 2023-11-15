import Player from './components/Player.jsx';
import TimerChallenge from './components/TimerChallenge.jsx';

function App() {
  return (
    <>
      <Player />
      <div id="challenges">
        <TimerChallenge tittle="Easy" targetTime={1}/>
        <TimerChallenge tittle="Not Easy" targetTime={5}/>
        <TimerChallenge tittle="Getting tougth" targetTime={10}/>
        <TimerChallenge tittle="Pros only" targetTime={15}/>
      </div>
    </>
  );
}

export default App;
