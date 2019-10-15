<?php include('server.php') ?>
<!DOCTYPE html>
<html lang="en" dir="ltr">
<head>
    <meta charset="utf-8">
    <title>CRAN SEARCH ENGINE</title>
    <meta name="viewport" content="width=device-width">
    <link rel="stylesheet" href="./css/styleResults.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
</head>

<body>

<form action="results.php" method="post">
    <div class="header">
        <label>CRAN</label>
        <?php session_start(); ?>
        <input type="text" name="query" value="<?php echo $_SESSION['key']; ?>"> 
        <button class="btn" type="submit" name="search">Search</button>
    </div>


    <div class="filter">
        <div class="box">
            <select name="format">
                <option value="*">All Formats</option>
                <option value="pdf">Pdf</option>
                <option value="txt">Txt</option>
                <option value="csv">Csv</option>
            </select>
        </div>
        <div class="box">
            <select name="language">
                <option value="*">Any Language</option>
                <option value="english">English</option>
                <option value="french">French</option>
            </select>

        </div>
        <div class="box">
            <select name="publicationDate">
                <option value="*">Any time</option>
                <option value="thisWeek">This week</option>
                <option value="thisMonth">This Month</option>
                <option value="thisYear">This Year</option>
            </select>
        </div>
    </div>
</form>
<?php include('curl.php') ?>
</body>
</html>