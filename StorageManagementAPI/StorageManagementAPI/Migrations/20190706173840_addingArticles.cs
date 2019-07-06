using Microsoft.EntityFrameworkCore.Migrations;

namespace StorageManagementAPI.Migrations
{
    public partial class addingArticles : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "articles",
                columns: table => new
                {
                    id = table.Column<string>(nullable: false),
                    label = table.Column<string>(nullable: true),
                    price = table.Column<float>(nullable: false),
                    minStock = table.Column<int>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_articles", x => x.id);
                });
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "articles");
        }
    }
}
